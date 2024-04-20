package com.example.finalproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.Coroutines
import com.example.finalproject.model.Recipe
import com.example.finalproject.repositories.RecipeRepository
import kotlinx.coroutines.Job

class RecipesListViewModel(private val repository: RecipeRepository) : ViewModel() {
    private lateinit var job: Job
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes
    fun fetchRecipes(query: String) {
        job = Coroutines.ioTheMain(
            { repository.getRecipes(query) },
            {
                println(it)
                _recipes.value = it?.hits?.map{hit -> hit.recipe}
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}

