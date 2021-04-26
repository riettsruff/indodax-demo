package com.tr.indodaxdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tr.indodaxdemo.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryFragment extends Fragment {
  private RecyclerView recyclerViewTransactionHistory;
  private TransactionHistoryAdapter transactionHistoryAdapter;
  private List<Transaction> transactionList = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_transaction_history, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupView(view);
    setupRecyclerView();
    setTransactionHistoryData();
  }

  private void setupView(View view) {
    recyclerViewTransactionHistory = view.findViewById(R.id.recycler_view_transaction_history);
  }

  private void setupRecyclerView() {
    transactionHistoryAdapter = new TransactionHistoryAdapter(transactionList);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerViewTransactionHistory.setLayoutManager(layoutManager);
    recyclerViewTransactionHistory.setAdapter(transactionHistoryAdapter);
  }

  private void setTransactionHistoryData() {
    transactionHistoryAdapter.setData();
  }
}