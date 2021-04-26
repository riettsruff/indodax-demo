package com.tr.indodaxdemo.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.tr.indodaxdemo.model.Pair;
import com.tr.indodaxdemo.model.Summaries;
import com.tr.indodaxdemo.model.TickerMap;

import java.util.List;

public interface IndodaxAPIEndpoint {
  @GET("pairs")
  Call<List<Pair>> getPairs();

  @GET("ticker/{pair_id}")
  Call<TickerMap> getTickerByPairId(@Path("pair_id") String pairId);

  @GET("summaries")
  Call<Summaries> getSummaries();
}
