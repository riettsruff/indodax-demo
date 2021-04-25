package com.tr.indodaxdemo.model;

import java.util.List;

public class Account {
  private String full_name;
  private String username;
  private String email;
  private String password;
  private double total_asset;
  private List<Wallet> wallets;
  private List<Transaction> transactions;

  public Account() {
  }

  public Account(String full_name, String username, String email, String password) {
      this.full_name = full_name;
      this.username = username;
      this.email = email;
      this.password = password;
  }

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

  public List<Wallet> getWallets() {
    return wallets;
  }

  public void setWallets(List<Wallet> wallets) {
    this.wallets = wallets;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }
}
