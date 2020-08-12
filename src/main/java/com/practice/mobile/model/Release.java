package com.practice.mobile.model;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class Release {
  private String announceDate;
  private String priceEur;

  public String getAnnounceDate() {
    return announceDate;
  }

  public void setAnnounceDate(String announceDate) {
    this.announceDate = announceDate;
  }

  public String getPriceEur() {
    return priceEur;
  }

  public void setPriceEur(String priceEur) {
    this.priceEur = priceEur;
  }

  @Override
  public String toString() {
    return "Release{"
        + "announceDate='"
        + announceDate
        + '\''
        + ", priceEur='"
        + priceEur
        + '\''
        + '}';
  }
}
