package com.tr.indodaxdemo.model;

import java.util.Date;

public class Transaction {
  private String coin_id;
  private Date date;
  private double price;

  public String getCoin_id() {
    return coin_id;
  }

  public void setCoin_id(String coin_id) {
    this.coin_id = coin_id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
