package com.aj.stocks.feed;

import com.aj.stocks.bean.Stockfeed;
import com.aj.stocks.thread.FeedThread;
import com.aj.stocks.util.CleanseKeywords;
import com.aj.stocks.util.TimestampConverters;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class StockFeedThread implements FeedThread {
  private CountDownLatch countDownLatch;

  private String country;
  private String feedUrl;

  public StockFeedThread(String country, String feedUrl, CountDownLatch countDownLatch) {
    this.country = country;
    this.feedUrl = feedUrl;
    this.countDownLatch = countDownLatch;
  }

  @Override
  public List<Stockfeed> call() throws Exception {
    List<Stockfeed> stockfeeds = new ArrayList<>();

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

    Document document = dbf.newDocumentBuilder().parse(new URL(feedUrl).openStream());

    NodeList entryNodes = document.getElementsByTagName("entry");

    for (int i = 0; i < entryNodes.getLength(); i++) {
      Node node = entryNodes.item(i);
      if (node.getNodeType() != Node.ELEMENT_NODE) continue;

      Element entry = (Element) node;

      String title = entry.getElementsByTagName("title").item(0).getTextContent();
      if (CleanseKeywords.containsKeywords(title) == false) continue;

      stockfeeds.add(
          Stockfeed.builder()
              .country(this.country)
              .text(CleanseKeywords.trimHtmlTags(title.trim()))
              .url(
                  entry
                      .getElementsByTagName("link")
                      .item(0)
                      .getAttributes()
                      .getNamedItem("href")
                      .getTextContent()
                      .trim())
              .feeddate(
                  TimestampConverters.convertFeedDatetime(
                      entry.getElementsByTagName("updated").item(0).getTextContent().trim()))
              .build());
    }

    countDownLatch.countDown();
    return stockfeeds;
  }
}
