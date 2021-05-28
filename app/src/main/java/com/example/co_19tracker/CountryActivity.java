package com.example.co_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.co_19tracker.api.ApiUtilities;
import com.example.co_19tracker.api.CounterData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CounterData> list;
    private ProgressDialog dialog;
    private EditText searchBar;
    CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.countries);
        list = new ArrayList<>();
        searchBar=findViewById(R.id.searchBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new CountryAdapter(this, list);
        recyclerView.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..........");
        dialog.setCancelable(false);
        dialog.show();

        ApiUtilities.getApiInterface().getCounterData().enqueue(new Callback<List<CounterData>>() {
            @Override
            public void onResponse(Call<List<CounterData>> call, Response<List<CounterData>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CounterData>> call, Throwable t) {
                Toast.makeText(CountryActivity.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }

            private void filter(String text) {
                List<CounterData> filterList =new ArrayList<>();
                for(CounterData items : list){
                    if(items.getCountry().toLowerCase().contains(text.toLowerCase())){
                        filterList.add(items);
                    }
                }
                adapter.filterList(filterList);
            }
        });
    }
}

