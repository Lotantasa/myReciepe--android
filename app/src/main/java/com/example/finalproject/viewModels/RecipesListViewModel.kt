package com.example.finalproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Coroutines
import com.example.finalproject.model.Hit
import com.example.finalproject.model.Recipe
import com.example.finalproject.model.RecipeResponse
import com.example.finalproject.repositories.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class RecipesListViewModel(private val repository: RecipeRepository) : ViewModel() {
    private lateinit var job: Job
    private val _recipes = MutableLiveData<List<Hit>>()
    val recipes: LiveData<List<Hit>> get() = _recipes
    fun fetchRecipes(query: String) {
        job = Coroutines.ioTheMain(
            { repository.getRecipes(query) },
            {
                _recipes.value = it
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}

