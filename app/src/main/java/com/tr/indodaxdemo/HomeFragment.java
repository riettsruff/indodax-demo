package com.tr.indodaxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.activity.DetailCoinActivity;
import com.tr.indodaxdemo.model.CoinSummary;
import com.tr.indodaxdemo.model.Summaries;
import com.tr.indodaxdemo.model.Pair;
import com.tr.indodaxdemo.retrofit.IndodaxAPIService;
import com.tr.indodaxdemo.service.CurrencyFormat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    View homeFragment;
    TextView totalAssetTextView;

    private RecyclerView recyclerViewCoinSummary;
    private ProgressBar progressBarCoinSummary;
    private CoinSummaryAdapter coinSummaryAdapter;
    private List<CoinSummary> coinSummaryList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshHome;

    private FirebaseAuth fAuth;
    private DatabaseReference fAccountsRootRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeFragment = inflater.inflate(R.layout.fragment_home, container,false);

        totalAssetTextView = homeFragment.findViewById(R.id.total_asset_value);

        fAuth = FirebaseAuth.getInstance();
        fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");

        return homeFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshHome = view.findViewById(R.id.swipe_refresh_home);

        setupView(view);
        setupRecyclerView();
        getDataFromAPI();

        swipeRefreshHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshHome.setRefreshing(false);
            }
        });

        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fTotalAssetRef = fAccountsRootRef.child(UID).child("total_asset");

        fTotalAssetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalAssetTextView.setText(CurrencyFormat.format(snapshot.getValue(Double.class), "in", "ID"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupView(View view) {
        recyclerViewCoinSummary = view.findViewById(R.id.recycler_view_coin_summary);
        progressBarCoinSummary = view.findViewById(R.id.progress_bar_coin_summary);
    }

    private void setupRecyclerView() {
        coinSummaryAdapter = new CoinSummaryAdapter(coinSummaryList, new CoinSummaryAdapter.OnAdapterListener() {
            @Override
            public void onClick(CoinSummary coinSummary) {
                Intent intent = new Intent(getContext(), DetailCoinActivity.class);
                intent.putExtra("coin_symbol", coinSummary.getSymbol());
                intent.putExtra("coin_name", coinSummary.getName());
                intent.putExtra("pair_id", coinSummary.getPair_id());
                intent.putExtra("coin_percentage", coinSummary.getPercentage());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCoinSummary.setLayoutManager(layoutManager);
        recyclerViewCoinSummary.setAdapter(coinSummaryAdapter);
    }

    private void getDataFromAPI() {
        progressBarCoinSummary.setVisibility(View.VISIBLE);

        Call<List<Pair>> callPairs = (Call<List<Pair>>) IndodaxAPIService.getPairs();
        Call<Summaries> callSummaries = (Call<Summaries>) IndodaxAPIService.getSummaries();

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
