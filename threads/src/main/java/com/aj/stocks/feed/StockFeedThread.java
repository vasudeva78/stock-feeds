package com.aj.stocks.feed;

import com.aj.stocks.bean.Stockfeed;
import com.aj.stocks.thread.FeedThread;
import com.aj.stocks.util.CleanseKeywords;
import com.aj.stocks.util.TimestampConverters;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

    domBasedUrlFeedReader(stockfeeds);
//    staxBasedUrlFeedReader(stockfeeds);

    countDownLatch.countDown();
    return stockfeeds;
  }

  private void domBasedUrlFeedReader(List<Stockfeed> stockfeeds) throws ParserConfigurationException, SAXException, IOException {
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
  }

  private void staxBasedUrlFeedReader(List<Stockfeed> stockfeeds)  {
    XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

    try {
      XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(
          new URL(feedUrl).openStream(),"UTF-8");

      while (reader.hasNext()) {
        int eventType = reader.next();

        if (eventType == XMLEvent.START_ELEMENT && "entry".equals(reader.getName().getLocalPart())) {
          while(reader.hasNext()) {
            eventType = reader.next();
;

            if (eventType == XMLEvent.START_ELEMENT){

              String title = "";
              String link = "";
              String updated = "";

              switch (reader.getName().getLocalPart()) {
                case "title":
                  eventType = reader.next();
                  if (eventType == XMLEvent.CHARACTERS) {
                    title = reader.getText();
//                    stockfeed.setText(title);
                  }
                  break;

                case "link":
                  link = reader.getAttributeValue(null, "href");
//                  stockfeed.setUrl(link);
                  break;

                case "updated":
                  eventType = reader.next();
                  if (eventType == XMLEvent.CHARACTERS) {
                    updated = reader.getText();
//                    stockfeed.setFeeddate(TimestampConverters.convertFeedDatetime(updated));
                    System.out.println("updated 1 => "+updated);
                  }
                  break;
              }

           System.out.println("title => "+title);
           System.out.println("link => "+link);
           System.out.println("updated => "+updated);

              if (CleanseKeywords.containsKeywords(title) == true){

//                stockfeeds.add( Stockfeed.builder()
//                    .country("IN")
//                    .text(CleanseKeywords.trimHtmlTags(title.trim()))
//                    .url(link)
//                    .feeddate( TimestampConverters.convertFeedDatetime(updated))
//                    .build());

              }
            }
          }
        }
      }
    } catch (Exception e) {
     log.error("Error while parsing feed urls => ",e);
    }
  }
}
