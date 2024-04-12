package com.example.finalproject.repositories

import android.nfc.Tag
import android.util.Log
import com.example.finalproject.api.RecipesApi
import com.example.finalproject.api.SafeApiRequest
import com.example.finalproject.model.Hit
import com.example.finalproject.model.Recipe
import com.example.finalproject.model.RecipeResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository (private val api: RecipesApi) : SafeApiRequest() {
    suspend fun getRecipes(query: String) = apiRequest {
        api.getAllRecipes(
            query = query,
            appId = "546a4f67",
            appKey = "aba8a9a207fd136545e0d95803581aa5",
            imageSize = "SMALL"
        )
    }
}
