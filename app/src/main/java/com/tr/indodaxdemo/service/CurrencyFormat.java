package com.tr.indodaxdemo.service;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
  public static String format(double currency, String languageCode, String countryCode) {
    return NumberFormat.getCurrencyInstance(new Locale(languageCode, countryCode)).format(currency);
  }
}
