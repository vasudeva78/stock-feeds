package com.aj.stocks.util;

import org.apache.commons.lang3.StringUtils;

public class CleanseKeywords {

  private static final String[] htmlTags = new String[] {"<b>", "</b>"};

  private static final String[] textSeachKeywords =
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
        "spin off",
        "bonus",
        "demerger",
        "split",
        "below rs",
        "under rs",
        "below £",
        "under £"
      };

  public static boolean containsKeywords(String input) {
    return StringUtils.containsAnyIgnoreCase(input, textSeachKeywords);
  }

  public static String trimHtmlTags(String input) {
    return StringUtils.replaceEach(input, htmlTags, new String[] {"", ""});
  }
}
