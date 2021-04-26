package com.tr.indodaxdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
  private List<Transaction> transactionList = new ArrayList<>();
  private FirebaseAuth fAuth;
  private DatabaseReference fAccountsRootRef;

  public TransactionHistoryAdapter(List<Transaction> transactionList) {
    this.transactionList = transactionList;
    this.fAuth = FirebaseAuth.getInstance();
    this.fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
      LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction_history, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Transaction transaction = transactionList.get(position);

    holder.dateValue.setText(transaction.getDate());
    holder.totalCoinsValue.setText(String.valueOf(transaction.getTotal_coins()));
    holder.totalRupiahValue.setText(String.valueOf(transaction.getTotal_rupiah()));
  }

  @Override
  public int getItemCount() {
    return transactionList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView dateValue;
    TextView totalCoinsValue;
    TextView totalRupiahValue;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      dateValue = itemView.findViewById(R.id.date_value);
      totalCoinsValue = itemView.findViewById(R.id.total_coins_value);
      totalRupiahValue = itemView.findViewById(R.id.total_rupiah_value);
    }
  }

  public void setData() {
    String UID = fAuth.getCurrentUser().getUid();
    DatabaseReference fTransactionsRef = fAccountsRootRef.child(UID).child("transactions");

    fTransactionsRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        transactionList.clear();

        for(DataSnapshot ds : snapshot.getChildren()) {
          Transaction transaction = ds.getValue(Transaction.class);
          transactionList.add(transaction);
        }

        notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }
}