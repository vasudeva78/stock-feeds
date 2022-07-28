package com.aj.stocks.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampConverters {

  private static final DateTimeFormatter feedDateFormatter =
      DateTimeFormatter.ofPattern("yyyy-M-d'T'H:m:s'Z'");

  public static Timestamp convertFeedDatetime(String dateToConvert) {
    LocalDateTime convertedTimestamp = LocalDateTime.parse(dateToConvert, feedDateFormatter);
    return Timestamp.valueOf(convertedTimestamp);
  }
}
