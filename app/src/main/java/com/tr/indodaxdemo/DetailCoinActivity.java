package com.tr.indodaxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DetailCoinActivity extends AppCompatActivity {
  FirebaseAuth fAuth;

  TextView buy_coinSymbol;
  EditText buy_totalRupiahEditText;
  TextView buy_estimationValue;
  Button buy_buyButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_coin);

    getSupportActionBar().setTitle(
      getIntent().getStringExtra("coin_symbol") + " | " + getIntent().getStringExtra("coin_name")
    );

    Bundle bundle = new Bundle();
    bundle.putString("pair_id", getIntent().getStringExtra("pair_id"));
    bundle.putString("coin_symbol", getIntent().getStringExtra("coin_symbol"));
    bundle.putString("coin_name", getIntent().getStringExtra("coin_name"));
    bundle.putString("coin_percentage", getIntent().getStringExtra("coin_percentage"));

    DetailCoinFragment detailCoinFragment = new DetailCoinFragment();
    detailCoinFragment.setArguments(bundle);

    BuyCoinFragment buyCoinFragment = new BuyCoinFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailCoinFragment).commit();

    fAuth = FirebaseAuth.getInstance();
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (fAuth.getCurrentUser() == null){
      startActivity(new Intent(DetailCoinActivity.this, LoginActivity.class));
      finish();
    }
  }
}