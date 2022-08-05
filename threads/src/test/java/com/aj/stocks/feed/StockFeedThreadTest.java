package com.aj.stocks.feed;

import com.aj.stocks.bean.Stockfeed;
import com.aj.stocks.util.CleanseKeywords;
import com.aj.stocks.util.TimestampConverters;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

@MicronautTest
class StockFeedThreadTest {

//  @Test
//      void callJacksonXml(){
//    String feedUrl = "https://www.google.co.uk/alerts/feeds/09090937536296712061/1850845371402009510";
//
//    XmlMapper xmlMapper = new XmlMapper();
//    try {
//      Feed feed = xmlMapper.readValue(new URL(feedUrl).openStream(), Feed.class);
//
//      System.out.println("feed => "+feed);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//  }

//    @Test
    void callStaxAPI() {
        String feedUrl = "https://www.google.co.uk/alerts/feeds/09090937536296712061/1850845371402009510";

        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        try {
            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(
                    new URL(feedUrl).openStream(),"UTF-8");

            while (reader.hasNext()) {
               int eventType = reader.next();
                if (eventType == XMLEvent.START_ELEMENT && "entry".equals(reader.getName().getLocalPart())) {
                    System.out.println("ele => "+reader.getName().getLocalPart());

                    while(reader.hasNext()) {
                      eventType = reader.next();
                      if (eventType == XMLEvent.START_ELEMENT){

                          String title = "";
                          String link = "";
                          String updated = "";

                        switch (reader.getName().getLocalPart()) {
                          case "title":
                            eventType = reader.next();
                            if (eventType == XMLEvent.CHARACTERS) {
                                title = reader.getText();
                              System.out.println("title => "+ title);
                            }
                            break;

                          case "link":
                              link = reader.getAttributeValue(null, "href");
                              System.out.println("link => "+ link );
                            break;

                          case "updated":
                            eventType = reader.next();
                            if (eventType == XMLEvent.CHARACTERS) {
                                updated = reader.getText();
                              System.out.println("updated => "+ updated);
                            }
                            break;
                        }

                          if (CleanseKeywords.containsKeywords(title) == true){
                              Stockfeed stockfeed = Stockfeed.builder()
                                  .country("IN")
                                  .text(CleanseKeywords.trimHtmlTags(title.trim()))
                                  .url(link)
                                  .feeddate( TimestampConverters.convertFeedDatetime(updated))
                                  .build();

                              System.out.println("stockfeed => "+stockfeed);
                          }

                      }
                     }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}