package com.tr.indodaxdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tr.indodaxdemo.model.CoinSummary;
import com.tr.indodaxdemo.model.CoinWallet;
import com.tr.indodaxdemo.model.Pair;
import com.tr.indodaxdemo.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class CoinWalletAdapter extends RecyclerView.Adapter<CoinWalletAdapter.ViewHolder> {
  private List<CoinWallet> coinWalletList = new ArrayList<>();
  private OnAdapterListener adapterListener;
  private FirebaseAuth fAuth;
  private DatabaseReference fAccountsRootRef;

  public CoinWalletAdapter(List<CoinWallet> coinWalletList, OnAdapterListener adapterListener) {
    this.coinWalletList = coinWalletList;
    this.adapterListener = adapterListener;
    this.fAuth = FirebaseAuth.getInstance();
    this.fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");
  }

  @NonNull
  @Override
  public CoinWalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
      LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_coin_wallet, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull CoinWalletAdapter.ViewHolder holder, int position) {
    CoinWallet coinWallet = coinWalletList.get(position);

    Picasso.get().load(coinWallet.getIcon()).fit().centerCrop().into(holder.coinIcon);

    holder.coinSymbol.setText(coinWallet.getSymbol());
    holder.coinTotal.setText(coinWallet.getTotal());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        adapterListener.onClick(coinWallet);
      }
    });
  }

  @Override
  public int getItemCount() {
    return coinWalletList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView coinIcon;
    TextView coinSymbol;
    TextView coinTotal;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      coinIcon = itemView.findViewById(R.id.coin_icon);
      coinSymbol = itemView.findViewById(R.id.coin_symbol);
      coinTotal = itemView.findViewById(R.id.coin_total);
    }
  }

  public void setData(List<Pair> pairs) {
    String UID = fAuth.getCurrentUser().getUid();
    DatabaseReference fWalletsRef = fAccountsRootRef.child(UID).child("wallets");

    fWalletsRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        coinWalletList.clear();
        List<Wallet> walletList = new ArrayList<>();

        for(DataSnapshot ds : snapshot.getChildren()) {
          Wallet wallet = ds.getValue(Wallet.class);
          walletList.add(wallet);
        }

        for(Pair pair : pairs) {
          if(pair.getBase_currency().equals("idr")) {
            CoinWallet coinWallet = new CoinWallet();
            coinWallet.setIcon(pair.getUrl_logo_png());
            coinWallet.setSymbol(pair.getTraded_currency_unit());
            coinWallet.setTotal("0 " + pair.getTraded_currency_unit());

            for(Wallet wallet : walletList) {
              if(pair.getId().equals(wallet.getCoin_id())) {
                coinWallet.setTotal(String.valueOf(wallet.getTotal_coins()) + " " + pair.getTraded_currency_unit());
                break;
              }
            }

            coinWalletList.add(coinWallet);
          }
        }

        notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  interface OnAdapterListener {
    void onClick(CoinWallet coinWallet);
  }
}
