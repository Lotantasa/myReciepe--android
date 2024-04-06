package com.example.finalproject.api;

import com.example.finalproject.model.MusicalsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RecipesApi {

    @Headers("Api-Key: 1234")
    @GET("recipes/v2?type=public&q=chicken&app_id=546a4f67&app_key" +
            "=aba8a9a207fd136545e0d95803581aa5&imageSize=SMALL")
    Call<MusicalsResult> getAllRecipes();
}
