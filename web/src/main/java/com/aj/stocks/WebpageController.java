package com.aj.stocks;

import com.aj.stocks.bean.Stockfeed;
import com.aj.stocks.repository.StockfeedRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.views.View;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Controller("/html")
@Slf4j
public class WebpageController {

  protected StockfeedRepository stockfeedRepository;

  public WebpageController(StockfeedRepository stockfeedRepository) { // <3>
    this.stockfeedRepository = stockfeedRepository;
  }

  @View("index")
  @Get("/feeds/{country}")
  public HttpResponse<Map<String, List<Stockfeed>>> getFeeds(
      @PathVariable(name = "country") String country) {
    List<Stockfeed> stockfeeds = stockfeedRepository.findAllStockFeeds(country);
    return HttpResponse.ok(Map.of("feeds", stockfeeds));
  }
}
