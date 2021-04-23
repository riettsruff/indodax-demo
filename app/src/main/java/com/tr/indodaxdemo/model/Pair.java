package com.tr.indodaxdemo.model;

public class Pair {
  private String id;
  private String symbol;
  private String base_currency;
  private String traded_currency;
  private String traded_currency_unit;
  private String description;
  private String ticker_id;
  private double trade_min_base_currency;
  private double trade_fee_percent;
  private String url_logo;
  private String url_logo_png;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getBase_currency() {
    return base_currency;
  }

  public void setBase_currency(String base_currency) {
    this.base_currency = base_currency;
  }

  public String getTraded_currency() {
    return traded_currency;
  }

  public void setTraded_currency(String traded_currency) {
    this.traded_currency = traded_currency;
  }

  public String getTraded_currency_unit() {
    return traded_currency_unit;
  }

  public void setTraded_currency_unit(String traded_currency_unit) {
    this.traded_currency_unit = traded_currency_unit;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTicker_id() {
    return ticker_id;
  }

  public void setTicker_id(String ticker_id) {
    this.ticker_id = ticker_id;
  }

  public double getTrade_min_base_currency() {
    return trade_min_base_currency;
  }

  public void setTrade_min_base_currency(double trade_min_base_currency) {
    this.trade_min_base_currency = trade_min_base_currency;
  }

  public double getTrade_fee_percent() {
    return trade_fee_percent;
  }

  public void setTrade_fee_percent(double trade_fee_percent) {
    this.trade_fee_percent = trade_fee_percent;
  }

  public String getUrl_logo() {
    return url_logo;
  }

  public void setUrl_logo(String url_logo) {
    this.url_logo = url_logo;
  }

  public String getUrl_logo_png() {
    return url_logo_png;
  }

  public void setUrl_logo_png(String url_logo_png) {
    this.url_logo_png = url_logo_png;
  }
}
