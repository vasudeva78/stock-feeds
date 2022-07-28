package com.aj.stocks.util;

import io.github.cdimascio.dotenv.Dotenv;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@MicronautTest
class EnvironmentUtilsTest {

  @Test
  void split() {
    Dotenv dotenv = Dotenv.load();
    String ukFeedUrls[] = StringUtils.split(dotenv.get("ukFeedUrls", ""), "|");
    System.out.println("envs => " + ukFeedUrls.length);

    System.out.println("indiaFeedUrls => " + dotenv.get("indiaFeedUrls"));
  }
}
