package com.aj.stocks.repository;

import com.aj.stocks.bean.Stockfeed;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2) // <1>
public interface StockfeedRepository extends PageableRepository<Stockfeed, Integer> {

  @Query(
      "INSERT IGNORE INTO stockfeed(text,url,country,feeddate) VALUES (:text, :url, :country, :feeddate)")
  Optional<Stockfeed> saveIfNotExists(String text, String url, String country, Timestamp feeddate);

  @Query(
      "select id, text, url, feeddate, country from stockfeed where country = :country order by feeddate desc")
  List<Stockfeed> findAllStockFeeds(String country);

  @Query("delete from stockfeed where datediff(d, feeddate, current_timestamp) >= 7")
  void deleteStockFeedsOlderThan7Days();
}
