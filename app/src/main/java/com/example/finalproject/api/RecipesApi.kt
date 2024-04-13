package com.example.finalproject.api

import com.example.finalproject.model.RecipeResponse
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
 interface RecipesApi {
    @GET("api/recipes/v2/?type=public")
     suspend fun getAllRecipes(
        @Query("q") query: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("imageSize") imageSize: String
    ):  Response<RecipeResponse>

     companion object {
         operator fun invoke(): RecipesApi {
             val BASE_URL = "https://api.edamam.com/"
             val gson = GsonBuilder()
                 .setLenient()
                 .create()

             return Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build()
                 .create(RecipesApi::class.java)
         }
     }
}
