package com.aj.stocks.bean.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
  private String name;
}
