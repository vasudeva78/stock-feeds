package com.aj.stocks.bean.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {
  private String id;
  private String title;
  private String link;
  private String published;
  private String updated;
  private String content;
  private Author author;
}
