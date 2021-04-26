package com.tr.indodaxdemo.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndodaxAPIService {
  private static final String BASE_URL = "https://indodax.com/api/";

  private static Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build();

  private static IndodaxAPIEndpoint indodaxAPIEndpoint = retrofit.create(IndodaxAPIEndpoint.class);

  public static Object getSummaries() {
    return indodaxAPIEndpoint.getSummaries();
  }

  public static Object getPairs() {
    return indodaxAPIEndpoint.getPairs();
  }

  public static Object getTickerByPairId(String pairId) {
    return indodaxAPIEndpoint.getTickerByPairId(pairId);
  }
}