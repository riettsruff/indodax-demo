package com.tr.indodaxdemo.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndodaxAPIService {
  public static final String BASE_URL = "https://indodax.com/api/";

  public static Object getData(String target) {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    IndodaxAPIEndpoint indodaxAPIEndpoint = retrofit.create(IndodaxAPIEndpoint.class);

    switch(target) {
      case "pairs": return indodaxAPIEndpoint.getPairs();
      case "summaries": return indodaxAPIEndpoint.getSummaries();
      default: return null;
    }
  }
}
