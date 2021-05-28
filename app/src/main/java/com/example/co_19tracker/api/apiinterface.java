package com.example.co_19tracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiinterface {
    static  final String BASE_URL="https://corona.lmao.ninja/v2/";
    @GET("countries")
    Call<List<CounterData>>getCounterData();
}
