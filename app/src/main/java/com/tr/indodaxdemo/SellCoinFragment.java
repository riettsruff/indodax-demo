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

import com.tr.indodaxdemo.R;

public class SellCoinFragment extends Fragment {
  View sellCoinFragment;

  EditText totalCoinsEditText;
  TextView coinSymbol;
  TextView estimationValue;
  Button sellButton;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    sellCoinFragment = inflater.inflate(R.layout.fragment_sell_coin, container, false);

    totalCoinsEditText = sellCoinFragment.findViewById(R.id.total_coins_edit_text);
    coinSymbol = sellCoinFragment.findViewById(R.id.coin_symbol);
    estimationValue = sellCoinFragment.findViewById(R.id.estimation_value);
    sellButton = sellCoinFragment.findViewById(R.id.sell_button);

    return sellCoinFragment;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    coinSymbol.setText(getArguments().getString("coin_symbol"));
  }
}