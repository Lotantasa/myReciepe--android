package com.example.finalproject.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse (
    @SerializedName("hits") val hits: List<Hit>
)

data class Hit(
    @SerializedName("recipe") val recipe: Recipe
)