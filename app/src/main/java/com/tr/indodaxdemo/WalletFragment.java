package com.tr.indodaxdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tr.indodaxdemo.retrofit.IndodaxAPIService;
import com.tr.indodaxdemo.retrofit.IndodaxAPIEndpoint;
import com.tr.indodaxdemo.model.Pair;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet,container,false);
    }

    private void getDataFromAPI() {
        Call<List<Pair>> call = (Call<List<Pair>>) IndodaxAPIService.getData("pairs");

        call.enqueue(new Callback<List<Pair>>() {
            @Override
            public void onResponse(Call<List<Pair>> call, Response<List<Pair>> response) {
                if(response.code() != 200) {
                    // handle error here
                    return;
                }

                List<Pair> pairs = response.body();

                for(Pair pair : pairs) {
                    Log.d("Tag", pair.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Pair>> call, Throwable t) {

            }
        });
    }
}
