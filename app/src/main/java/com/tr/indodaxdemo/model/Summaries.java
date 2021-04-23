package com.tr.indodaxdemo.model;

import java.util.ArrayList;

public class Summaries {
  private ArrayList<Ticker> tickers;
  private ArrayList<Price24h> prices_24h;

  public ArrayList<Ticker> getTickers() {
    return tickers;
  }

  public void setTickers(ArrayList<Ticker> tickers) {
    this.tickers = tickers;
  }

  public ArrayList<Price24h> getPrices_24h() {
    return prices_24h;
  }

  public void setPrices_24h(ArrayList<Price24h> prices_24h) {
    this.prices_24h = prices_24h;
  }
}
