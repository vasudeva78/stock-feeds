package com.aj.stocks.util;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@MicronautTest
class CleanseKeywordsTest {

  String[] keywords =
      new String[] {
        "penny",
        "small cap",
        "small-cap",
        "shareholder",
        "dividend",
        "paying",
        "announce",
        "multibagger",
        "promoter",
        "holding",
        "spinoff",
        "bonus",
        "demerger",
        "split",
        "below rs"
      };

  @Test
  void containsKeywords() {
    String stringToValidate =
        "below Textile Stock Declares Record Date : Key Details - Goodreturns";

    boolean isContainStrings = StringUtils.containsAnyIgnoreCase(stringToValidate, keywords);

    System.out.println("isContainStrings => " + isContainStrings);
  }
}
