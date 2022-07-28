package com.aj.stocks.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class CleanseKeywords {

  private static final String[] htmlTags = new String[] {"<b>", "</b>"};

  private static final String[] textSeachKeywords =
      new String[] {
        "penny",
        "small cap",
        "small-cap",
        "shareholder",
        "dividend",
        "dividend paying",
        "announce",
        "multibagger",
        "promoter",
        "holding",
        "spinoff",
        "bonus",
        "demerger",
        "split"
      };

  public static boolean containsKeywords(String input) {
    String isPresent =
        Arrays.stream(textSeachKeywords)
            .filter(keyword -> StringUtils.containsAnyIgnoreCase(trimHtmlTags(input), keyword))
            .findFirst()
            .orElse("notFound");

    return isPresent.equals("notFound") ? false : true;
  }

  public static String trimHtmlTags(String input) {
    return StringUtils.replaceEach(input, htmlTags, new String[] {"", ""});
  }
}
