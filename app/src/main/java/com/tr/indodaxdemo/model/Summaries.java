package com.tr.indodaxdemo.model;

import java.util.Map;

public class Summaries {
  private Map<String, Ticker> tickers;
  private Map<String, String> prices_24h;

  public Map<String, Ticker> getTickers() {
    return tickers;
  }

  public void setTickers(Map<String, Ticker> tickers) {
    this.tickers = tickers;
  }

  public Map<String, String> getPrices_24h() {
    return prices_24h;
  }

  public void setPrices_24h(Map<String, String> prices_24h) {
    this.prices_24h = prices_24h;
  }
}
