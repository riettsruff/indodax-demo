package com.tr.indodaxdemo.retrofit;

import com.tr.indodaxdemo.model.Pair;
import com.tr.indodaxdemo.model.Summaries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IndodaxAPIEndpoint {
  @GET("pairs")
  Call<List<Pair>> getPairs();

  @GET("summaries")
  Call<Summaries> getSummaries();
}
