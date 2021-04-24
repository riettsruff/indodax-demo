package com.tr.indodaxdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tr.indodaxdemo.model.CoinSummary;
import com.tr.indodaxdemo.model.Summaries;
import com.tr.indodaxdemo.model.Pair;
import com.tr.indodaxdemo.retrofit.IndodaxAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewCoinSummary;
    private ProgressBar progressBarCoinSummary;
    private CoinSummaryAdapter coinSummaryAdapter;
    private List<CoinSummary> coinSummaryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupView(view);
        setupRecyclerView();
        getDataFromAPI();
    }

    private void setupView(View view) {
        recyclerViewCoinSummary = view.findViewById(R.id.recycler_view_coin_summary);
        progressBarCoinSummary = view.findViewById(R.id.progress_bar_coin_summary);
    }

    private void setupRecyclerView() {
        coinSummaryAdapter = new CoinSummaryAdapter(coinSummaryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCoinSummary.setLayoutManager(layoutManager);
        recyclerViewCoinSummary.setAdapter(coinSummaryAdapter);
    }

    private void getDataFromAPI() {
        progressBarCoinSummary.setVisibility(View.VISIBLE);

        Call<List<Pair>> callPairs = (Call<List<Pair>>) IndodaxAPIService.getData("pairs");
        Call<Summaries> callSummaries = (Call<Summaries>) IndodaxAPIService.getData("summaries");

        callPairs.enqueue(new Callback<List<Pair>>() {
            @Override
            public void onResponse(Call<List<Pair>> call, Response<List<Pair>> pairsResponse) {
                if(pairsResponse.isSuccessful()) {
                    callSummaries.enqueue(new Callback<Summaries>() {
                        @Override
                        public void onResponse(Call<Summaries> call, Response<Summaries> summariesResponse) {
                            progressBarCoinSummary.setVisibility(View.GONE);

                            if(pairsResponse.isSuccessful()) {
                                List<Pair> pairs = pairsResponse.body();
                                Summaries summaries = summariesResponse.body();

                                coinSummaryAdapter.setData(pairs, summaries);
                            }
                        }

                        @Override
                        public void onFailure(Call<Summaries> call, Throwable t) {
                            progressBarCoinSummary.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Pair>> call, Throwable t) {
                progressBarCoinSummary.setVisibility(View.GONE);
            }
        });
    }
}
