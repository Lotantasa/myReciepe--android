package com.example.finalproject.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface MusicalApi {

    @Headers("Api-Key: 1234")
    @GET("Events")
    Call<MusicalsResult> getAllMusicals();
}
