package com.tr.indodaxdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DebugUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.R;
import com.tr.indodaxdemo.model.Ticker;
import com.tr.indodaxdemo.model.Transaction;
import com.tr.indodaxdemo.model.Wallet;
import com.tr.indodaxdemo.service.CurrencyFormat;
import com.tr.indodaxdemo.service.DateFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class BuyCoinFragment extends Fragment {
  View buyCoinFragment;
  TextView coinSymbol;
  EditText totalRupiahEditText;
  TextView estimationValue;
  Button buyButton;
  TextView lastPriceValueTextView;
  String lastPriceValue;
  TextView coinLabel;

  private FirebaseAuth fAuth;
  private DatabaseReference fAccountsRootRef;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    buyCoinFragment = inflater.inflate(R.layout.fragment_buy_coin, container, false);

    coinSymbol = buyCoinFragment.findViewById(R.id.coin_symbol);
    totalRupiahEditText = buyCoinFragment.findViewById(R.id.total_rupiah_edit_text);
    estimationValue = buyCoinFragment.findViewById(R.id.estimation_value);
    buyButton = buyCoinFragment.findViewById(R.id.buy_button);
    coinLabel = buyCoinFragment.findViewById(R.id.count_label);

    lastPriceValueTextView = DetailCoinFragment.detailCoinFragment.findViewById(R.id.last_price_value);
    lastPriceValue = lastPriceValueTextView.getText().toString();

    fAuth = FirebaseAuth.getInstance();
    fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");

    buyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fWalletsRef = fAccountsRootRef.child(UID).child("wallets");
        DatabaseReference fTransactionsRef = fAccountsRootRef.child(UID).child("transactions");

        Transaction transaction = new Transaction();
        transaction.setCoin_id(getArguments().getString("pair_id"));
        transaction.setDate(DateFormat.dateToString(new Date(), "yyyy-MM-dd"));
        transaction.setTotal_coins(Double.parseDouble(estimationValue.getText().toString()));
        transaction.setTotal_rupiah(Double.parseDouble(totalRupiahEditText.getText().toString()));

        UUID transactionUID = UUID.randomUUID();

        fTransactionsRef.child(transactionUID.toString()).setValue(transaction);
        Toast.makeText(getContext(), "Transaction Successfully", Toast.LENGTH_SHORT);
      }
    });

    coinLabel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String totalRupiah = totalRupiahEditText.getText().toString();
        Double result = 0.0;

        lastPriceValueTextView = DetailCoinFragment.detailCoinFragment.findViewById(R.id.last_price_value);
        lastPriceValue = lastPriceValueTextView.getText().toString()
          .replace(".", "").replace(" ", "").replaceAll("[a-zA-Z]", "");

        try {
          result = Double.parseDouble(totalRupiah) / Double.parseDouble(lastPriceValue);
        } catch (NumberFormatException e) {
          result = 0.0;
        } finally {
          estimationValue.setText(String.valueOf(result));
        }
      }
    });

    return buyCoinFragment;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    coinSymbol.setText(getArguments().getString("coin_symbol"));
  }
}