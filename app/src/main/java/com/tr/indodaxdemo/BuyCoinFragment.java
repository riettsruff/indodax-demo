package com.tr.indodaxdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BuyCoinFragment extends Fragment {
  View buyCoinFragment;
  TextView coinSymbol;
  EditText totalRupiahEditText;
  TextView estimationValue;
  Button buyButton;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    buyCoinFragment = inflater.inflate(R.layout.fragment_buy_coin, container, false);

    coinSymbol = buyCoinFragment.findViewById(R.id.coin_symbol);
    totalRupiahEditText = buyCoinFragment.findViewById(R.id.total_rupiah_edit_text);
    estimationValue = buyCoinFragment.findViewById(R.id.estimation_value);
    buyButton = buyCoinFragment.findViewById(R.id.buy_button);

    return buyCoinFragment;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    coinSymbol.setText(getArguments().getString("coin_symbol"));
  }
}