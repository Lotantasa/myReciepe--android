package com.example.finalproject.api

import com.example.finalproject.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query
 interface RecipesApi {
    @GET("api/recipes/v2/?type=public")
    suspend fun getAllRecipes(
        @Query("q") query: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("imageSize") imageSize: String
    ):  List<Recipe>
}
