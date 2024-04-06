package com.example.finalproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Recipe
import com.example.finalproject.repositories.RecipeRepository
import kotlinx.coroutines.launch

class RecipesListViewModel: ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    fun fetchRecipes(query: String) {
        viewModelScope.launch {
            val recipesList = RecipeRepository.getRecipes(query)
            _recipes.postValue(recipesList)
        }
    }
}