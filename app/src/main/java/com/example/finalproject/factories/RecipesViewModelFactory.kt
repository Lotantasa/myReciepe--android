package com.example.finalproject.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.repositories.RecipeRepository
import com.example.finalproject.viewModels.RecipesListViewModel

class RecipesViewModelFactory(private val repository: RecipeRepository):
    ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipesListViewModel(repository) as T
        }
}