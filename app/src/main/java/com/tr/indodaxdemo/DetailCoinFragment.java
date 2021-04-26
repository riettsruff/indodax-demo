package com.tr.indodaxdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.model.Ticker;
import com.tr.indodaxdemo.model.TickerMap;
import com.tr.indodaxdemo.model.Wallet;
import com.tr.indodaxdemo.retrofit.IndodaxAPIService;
import com.tr.indodaxdemo.service.CurrencyFormat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCoinFragment extends Fragment {
  View detailCoinFragment;
  ViewPager viewPagerMenuContent;
  TabLayout detailCoinTabLayout;

  SwipeRefreshLayout swipeRefreshDetailCoin;
  TextView lastPriceValue;
  TextView volumeCoin;
  TextView coinPercentage;
  TextView highValue;
  TextView lowValue;
  TextView totalCoinsValue;
  TextView totalRupiahValue;

  private FirebaseAuth fAuth;
  private DatabaseReference fAccountsRootRef;

  public static DetailCoinFragment getInstance() { return new DetailCoinFragment(); }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    detailCoinFragment = inflater.inflate(R.layout.fragment_detail_coin, container,false);

    swipeRefreshDetailCoin = detailCoinFragment.findViewById(R.id.swipe_refresh_detail_coin);
    lastPriceValue = detailCoinFragment.findViewById(R.id.last_price_value);
    volumeCoin = detailCoinFragment.findViewById(R.id.volume_coin);
    coinPercentage = detailCoinFragment.findViewById(R.id.coin_percentage);
    highValue = detailCoinFragment.findViewById(R.id.high_value);
    lowValue = detailCoinFragment.findViewById(R.id.low_value);
    totalCoinsValue = detailCoinFragment.findViewById(R.id.total_coins_value);
    totalRupiahValue = detailCoinFragment.findViewById(R.id.total_rupiah_value);

    viewPagerMenuContent = detailCoinFragment.findViewById(R.id.view_pager_menu_content);
    detailCoinTabLayout = detailCoinFragment.findViewById(R.id.detail_coin_tab_layout);

    fAuth = FirebaseAuth.getInstance();
    fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");

    setDetailCoinData();

    return detailCoinFragment;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    setupViewPagerMenuContent(viewPagerMenuContent);
    detailCoinTabLayout.setupWithViewPager(viewPagerMenuContent);

    detailCoinTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  private void setupViewPagerMenuContent(ViewPager viewPagerMenuContent) {
    Bundle bundle = new Bundle();
    bundle.putString("pair_id", getArguments().getString("pair_id"));
    bundle.putString("coin_symbol", getArguments().getString("coin_symbol"));
    bundle.putString("coin_name", getArguments().getString("coin_name"));
    bundle.putString("coin_percentage", getArguments().getString("coin_percentage"));

    BuyCoinFragment buyCoinFragment = new BuyCoinFragment();
    buyCoinFragment.setArguments(bundle);

    SellCoinFragment sellCoinFragment = new SellCoinFragment();
    sellCoinFragment.setArguments(bundle);

    TransactionHistoryFragment transactionHistoryFragment = new TransactionHistoryFragment();

    DetailCoinPageAdapter detailCoinPageAdapter = new DetailCoinPageAdapter(getChildFragmentManager());

    detailCoinPageAdapter.addFragment(buyCoinFragment, "Buy");
    detailCoinPageAdapter.addFragment(sellCoinFragment, "Sell");
    detailCoinPageAdapter.addFragment(transactionHistoryFragment, "History");

    viewPagerMenuContent.setAdapter(detailCoinPageAdapter);
  }

  private void setDetailCoinData() {
    Call<TickerMap> callTickerMap = (Call<TickerMap>) IndodaxAPIService.getTickerByPairId(getArguments().getString("pair_id"));

    callTickerMap.enqueue(new Callback<TickerMap>() {
      @Override
      public void onResponse(Call<TickerMap> call, Response<TickerMap> tickerMapResponse) {
        if(tickerMapResponse.isSuccessful()) {
          TickerMap tickerMap = tickerMapResponse.body();
          Ticker ticker = tickerMap.getTicker();

          lastPriceValue.setText(
            CurrencyFormat.format(
              Double.parseDouble(ticker.getLast()), "in", "ID").replace("Rp", ""
            ) + " IDR"
          );
          volumeCoin.setText("Vol: " + CurrencyFormat
            .format(Double.parseDouble(ticker.getVol_idr()), "in", "ID")
            .replace("Rp", "") + " IDR");

          Double _coinPercentage = Double.parseDouble(getArguments().getString("coin_percentage"));
          coinPercentage.setText(String.valueOf(_coinPercentage) + "%");

          if(_coinPercentage < 0.0) {
            lastPriceValue.setTextColor(Color.rgb(244, 80, 80));
            coinPercentage.setTextColor(Color.rgb(244, 80, 80));
          } else if (_coinPercentage > 0.0) {
            lastPriceValue.setTextColor(Color.rgb(58, 183, 100));
            coinPercentage.setTextColor(Color.rgb(58, 183, 100));
          } else {
            lastPriceValue.setTextColor(Color.rgb(75, 75, 75));
            coinPercentage.setTextColor(Color.rgb(75, 75, 75));
          }

          highValue.setText("High: " +
            CurrencyFormat
              .format(Double.parseDouble(ticker.getHigh()), "in", "ID")
              .replace("Rp", ""));
          lowValue.setText("Low: " +
            CurrencyFormat
              .format(Double.parseDouble(ticker.getLow()), "in", "ID")
              .replace("Rp", ""));

          String UID = fAuth.getCurrentUser().getUid();
          DatabaseReference fWalletsRef = fAccountsRootRef.child(UID).child("wallets");

          fWalletsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              List<Wallet> walletList = new ArrayList<>();

              for(DataSnapshot ds : snapshot.getChildren()) {
                walletList.add(ds.getValue(Wallet.class));
              }

              Boolean hasCoin = false;

              for(Wallet wallet : walletList) {
                if(wallet.getCoin_id().equals(getArguments().getString("pair_id"))) {
                  Double total = wallet.getTotal_coins();
                  Double last = Double.parseDouble(ticker.getLast());
                  Double totalCoins = total * last;

                  totalCoinsValue.setText(String.valueOf(total));
                  totalRupiahValue.setText(
                    CurrencyFormat
                      .format(totalCoins, "in", "ID").replace("Rp", "")
                  );

                  hasCoin = true;
                  break;
                }
              }

              if(!hasCoin) {
                totalCoinsValue.setText("0");
                totalRupiahValue.setText("0");
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
          });
        }
      }

      @Override
      public void onFailure(Call<TickerMap> call, Throwable t) {
      }
    });
  }

  private void setTransactionHistoryData() {

  }
}
