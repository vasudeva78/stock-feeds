package com.aj.stocks;

import com.aj.stocks.bean.Stockfeed;
import com.aj.stocks.repository.StockfeedRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ExecuteOn(TaskExecutors.IO) // <1>
@Controller("/api")
@Slf4j
public class FeedsController {
  protected StockfeedRepository stockfeedRepository;

  public FeedsController(StockfeedRepository stockfeedRepository) { // <3>
    this.stockfeedRepository = stockfeedRepository;
  }

  @Get("/feeds/{country}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Stockfeed> getFeeds(@PathVariable(name = "country") String country) {
    log.info("inside controller");

    return stockfeedRepository.findAllStockFeeds(country);
  }
}
