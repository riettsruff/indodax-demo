package com.tr.indodaxdemo;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tr.indodaxdemo.model.CoinSummary;
import com.tr.indodaxdemo.model.Summaries;
import com.tr.indodaxdemo.model.Ticker;
import com.tr.indodaxdemo.model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinSummaryAdapter extends RecyclerView.Adapter<CoinSummaryAdapter.ViewHolder> {
  private List<CoinSummary> coinSummaryList = new ArrayList<>();

  public CoinSummaryAdapter(List<CoinSummary> coinSummaryList) {
    this.coinSummaryList = coinSummaryList;
  }

  @NonNull
  @Override
  public CoinSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
      LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_coin_summary, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull CoinSummaryAdapter.ViewHolder holder, int position) {
    CoinSummary coinSummary = coinSummaryList.get(position);

    Picasso.get().load(coinSummary.getIcon()).fit().centerCrop().into(holder.coinIcon);

    holder.coinName.setText(coinSummary.getName());
    holder.coinSymbol.setText(coinSummary.getSymbol());

    Double coinPercentage = 0.0;

    try {
      coinPercentage = Double.parseDouble(coinSummary.getPercentage());
    } catch (NumberFormatException ex) {
      coinPercentage = 0.0;
    } finally {
      if(coinPercentage < 0.0) {
        holder.coinPercentage.setTextColor(Color.rgb(244, 80, 80));
      } else if (coinPercentage > 0.0) {
        holder.coinPercentage.setTextColor(Color.rgb(58, 183, 100));
      } else {
        holder.coinPercentage.setTextColor(Color.rgb(75, 75, 75));
      }

      holder.coinPercentage.setText(coinSummary.getPercentage() + "%");
    }
  }

  @Override
  public int getItemCount() {
    return coinSummaryList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView coinIcon;
    TextView coinSymbol;
    TextView coinName;
    TextView coinPercentage;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      coinIcon = itemView.findViewById(R.id.coin_icon);
      coinSymbol = itemView.findViewById(R.id.coin_symbol);
      coinName = itemView.findViewById(R.id.coin_name);
      coinPercentage = itemView.findViewById(R.id.coin_percentage);
    }
  }

  public void setData(List<Pair> pairs, Summaries summaries) {
    coinSummaryList.clear();

    Map<String, Ticker> tickers = summaries.getTickers();
    Map<String, String> prices_24h = summaries.getPrices_24h();

    for(Map.Entry<String, Ticker> tickerEntry : tickers.entrySet()) {
      String tickerId = tickerEntry.getKey();
      String pairId = tickerId.replace("_", "");
      Ticker ticker = tickerEntry.getValue();

      Double lastPrice = 0.0;
      Double price24h = 0.0;

      try {
        lastPrice = Double.valueOf(ticker.getLast());
        price24h = Double.valueOf(prices_24h.get(pairId));
      } catch (NumberFormatException ex) {
      } finally {
        Double pricePercentage =
          price24h == 0.0
            ? 0.0
            : ((lastPrice - price24h) / price24h);
        pricePercentage *= 100;

        for(Pair pair : pairs) {
          if(pair.getId().equals(pairId)) {
            CoinSummary coinSummary = new CoinSummary();
            coinSummary.setIcon(pair.getUrl_logo_png());
            coinSummary.setName(ticker.getName());
            coinSummary.setSymbol(pair.getTraded_currency_unit());
            coinSummary.setPercentage(String.format("%.2f", pricePercentage));
            coinSummaryList.add(coinSummary);

            break;
          }
        }
      }
    }

    notifyDataSetChanged();
  }
}
