package com.aj.stock.feed;

import com.aj.stocks.bean.Stockfeed;
import com.aj.stocks.feed.StockFeedThread;
import com.aj.stocks.repository.StockfeedRepository;
import com.aj.stocks.thread.FeedThread;
import io.github.cdimascio.dotenv.Dotenv;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Singleton
@ExecuteOn(TaskExecutors.SCHEDULED)
@Slf4j
public class FeedsInvoker {

  private final int executionTimeoutInSeconds = 60;

  StockfeedRepository stockfeedRepository;

  public FeedsInvoker(StockfeedRepository stockfeedRepository) { // <3>
    this.stockfeedRepository = stockfeedRepository;
  }

  Dotenv dotenv;

  @PostConstruct
  public void postConstruct() {
    dotenv = Dotenv.load();
  }

  // At minute 15 past every 6th hour.
  //  @Scheduled(cron = "15 */6 * * *")

  // At every 1 minute.
  //  @Scheduled(cron = "*/1 * * * *")

  // At every 5 minute.
  //  @Scheduled(cron = "*/5 * * * *")

  @Scheduled(cron = "15 */6 * * *")
  void callAllFeeds() throws Exception {
    String[] indiaFeedUrls = StringUtils.split(dotenv.get("indiaFeedUrls", ""), "|");
    String[] ukFeedUrls = StringUtils.split(dotenv.get("ukFeedUrls", ""), "|");

    int noOfFeeds = indiaFeedUrls.length + ukFeedUrls.length;
    CountDownLatch latch = new CountDownLatch(noOfFeeds);
    List<FeedThread> feedThreadsList = new ArrayList<>(noOfFeeds);

    Arrays.stream(indiaFeedUrls)
        .forEach(
            indiaFeedUrl -> {
              feedThreadsList.add(new StockFeedThread("IN", indiaFeedUrl, latch));
            });

    Arrays.stream(ukFeedUrls)
        .forEach(
            ukFeedUrl -> {
              feedThreadsList.add(new StockFeedThread("UK", ukFeedUrl, latch));
            });

    ExecutorService threadPool = Executors.newFixedThreadPool(noOfFeeds);
    List<Future<List<Stockfeed>>> feedsOutput =
        threadPool.invokeAll(feedThreadsList, executionTimeoutInSeconds, TimeUnit.SECONDS);

    latch.await(executionTimeoutInSeconds, TimeUnit.SECONDS);

    List<Stockfeed> finalFeeds =
        feedsOutput.stream()
            .flatMap(
                futureFeed -> {
                  try {
                    return futureFeed.get(executionTimeoutInSeconds, TimeUnit.SECONDS).stream();
                  } catch (Exception e) {
                    log.error("Error while getting all feeds ... ", feedThreadsList, e);
                  }
                  return null;
                })
            .collect(Collectors.toList());

    threadPool.shutdownNow();

    log.info("feed count => {}", finalFeeds.size());

    finalFeeds.forEach(
        stockFeedToSave -> {
          stockfeedRepository.saveIfNotExists(
              stockFeedToSave.getText(),
              stockFeedToSave.getUrl(),
              stockFeedToSave.getCountry(),
              stockFeedToSave.getFeeddate());
        });
  }
}
