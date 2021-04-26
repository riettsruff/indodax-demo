package com.tr.indodaxdemo;

import android.content.Intent;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tr.indodaxdemo.activity.DetailCoinActivity;
import com.tr.indodaxdemo.model.CoinWallet;
import com.tr.indodaxdemo.retrofit.IndodaxAPIService;
import com.tr.indodaxdemo.model.Pair;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    private RecyclerView recyclerViewCoinWallet;
    private ProgressBar progressBarCoinWallet;
    private CoinWalletAdapter coinWalletAdapter;
    private List<CoinWallet> coinWalletList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshWallet = view.findViewById(R.id.swipe_refresh_wallet);

        setupView(view);
        setupRecyclerView();
        getDataFromAPI();

        swipeRefreshWallet.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshWallet.setRefreshing(false);
            }
        });
    }

    private void setupView(View view) {
        recyclerViewCoinWallet = view.findViewById(R.id.recycler_view_coin_wallet);
        progressBarCoinWallet = view.findViewById(R.id.progress_bar_coin_wallet);
    }

    private void setupRecyclerView() {
        coinWalletAdapter = new CoinWalletAdapter(coinWalletList, new CoinWalletAdapter.OnAdapterListener() {
            @Override
            public void onClick(CoinWallet coinWallet) {
                Intent intent = new Intent(getContext(), DetailCoinActivity.class);
                intent.putExtra("coin_symbol", coinWallet.getSymbol());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCoinWallet.setLayoutManager(layoutManager);
        recyclerViewCoinWallet.setAdapter(coinWalletAdapter);
    }

    private void getDataFromAPI() {
        progressBarCoinWallet.setVisibility(View.VISIBLE);

        Call<List<Pair>> call = (Call<List<Pair>>) IndodaxAPIService.getPairs();

        call.enqueue(new Callback<List<Pair>>() {
            @Override
            public void onResponse(Call<List<Pair>> call, Response<List<Pair>> response) {
                progressBarCoinWallet.setVisibility(View.GONE);

                if(response.isSuccessful()) {
                    List<Pair> pairs = response.body();
                    coinWalletAdapter.setData(pairs);
                }
            }

            @Override
            public void onFailure(Call<List<Pair>> call, Throwable t) {
                progressBarCoinWallet.setVisibility(View.GONE);
            }
        });
    }
}
