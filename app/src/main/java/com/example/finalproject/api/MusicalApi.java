package com.example.finalproject.api;

import com.example.finalproject.model.MusicalsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface MusicalApi {

    @Headers("Api-Key: 1234")
    @GET("Events")
    Call<MusicalsResult> getAllMusicals();
}
