package com.aj.stocks.bean;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@MappedEntity()
@Getter
@Builder
@ToString
@Introspected
public class Stockfeed {
  @Id
  @GeneratedValue(GeneratedValue.Type.AUTO)
  private int id;

  @NotNull private String text;

  @NotNull private String url;

  @NotNull private String country;

  @NotNull private Timestamp feeddate;
}
