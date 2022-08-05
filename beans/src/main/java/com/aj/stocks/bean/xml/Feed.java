package com.aj.stocks.bean.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {
  private String id;
  private String title;
  private String link ;
  private String updated ;
  private List<Entry> entry ;
}
