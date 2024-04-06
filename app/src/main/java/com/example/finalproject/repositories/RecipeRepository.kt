package com.example.finalproject.repositories

import com.example.finalproject.api.RecipesApi
import com.example.finalproject.model.Recipe
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecipeRepository {
    private val BASE_URL = "https://api.edamam.com/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit =Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val recipesApi: RecipesApi = retrofit.create(RecipesApi::class.java)

    suspend fun getRecipes(query: String): List<Recipe> {
        return recipesApi.getAllRecipes(
            query = query,
            appId = "546a4f67",
            appKey = "aba8a9a207fd136545e0d95803581aa5",
            imageSize = "SMALL"
        )
    }
}
