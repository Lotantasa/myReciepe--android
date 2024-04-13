package com.example.finalproject.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("from") val from: Int,
    @SerializedName("to") val to: Int,
    @SerializedName("more") val more: Boolean,
    @SerializedName("hits") val hits: List<Hit>
)

data class Hit(
    @SerializedName("recipe") val recipe: Recipe
)