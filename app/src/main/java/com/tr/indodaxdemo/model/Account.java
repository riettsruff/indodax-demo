package com.tr.indodaxdemo.model;

import java.util.List;

public class Account {
  private String full_name;
  private String username;
  private String email;
  private String password;
  private double total_asset;
  private List<Wallet> wallet;
  private List<Transaction> transaction;

  public String getFull_name() {
    return full_name;
  }

  public void setFull_name(String full_name) {
    this.full_name = full_name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public double getTotal_asset() {
    return total_asset;
  }

  public void setTotal_asset(double total_asset) {
    this.total_asset = total_asset;
  }

  public List<Wallet> getWallet() {
    return wallet;
  }

  public void setWallet(List<Wallet> wallet) {
    this.wallet = wallet;
  }

  public List<Transaction> getTransaction() {
    return transaction;
  }

  public void setTransaction(List<Transaction> transaction) {
    this.transaction = transaction;
  }
}
