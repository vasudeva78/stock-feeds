package com.aj.stock.feed;

import com.aj.stocks.repository.StockfeedRepository;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@ExecuteOn(TaskExecutors.SCHEDULED)
@Slf4j
public class DatabaseCleanupInvoker {
  StockfeedRepository stockfeedRepository;

  public DatabaseCleanupInvoker(StockfeedRepository stockfeedRepository) { // <3>
    this.stockfeedRepository = stockfeedRepository;
  }

  // At 00:30 every Sunday
  @Scheduled(cron = "30 0 * * SUN")
  void deleteAllFeeds() throws Exception {
    stockfeedRepository.deleteStockFeedsOlderThan7Days();
  }
}
