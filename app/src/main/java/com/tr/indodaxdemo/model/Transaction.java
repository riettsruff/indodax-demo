package com.tr.indodaxdemo.model;

public class Transaction {
  private String coin_id;
  private String date;
  private double total_coins;
  private double total_rupiah;

  public String getCoin_id() {
    return coin_id;
  }

  public void setCoin_id(String coin_id) {
    this.coin_id = coin_id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getTotal_coins() {
    return total_coins;
  }

  public void setTotal_coins(double total_coins) {
    this.total_coins = total_coins;
  }

  public double getTotal_rupiah() {
    return total_rupiah;
  }

  public void setTotal_rupiah(double total_rupiah) {
    this.total_rupiah = total_rupiah;
  }
}
