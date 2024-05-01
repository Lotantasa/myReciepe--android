package com.example.finalproject.repositories

import com.example.finalproject.api.RecipesApi
import com.example.finalproject.api.SafeApiRequest

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
