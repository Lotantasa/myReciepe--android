package com.example.finalproject.views

import RecipeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.api.RecipesApi
import com.example.finalproject.factories.RecipesViewModelFactory
import com.example.finalproject.model.Recipe
import com.example.finalproject.repositories.RecipeRepository
import com.example.finalproject.viewModels.RecipesListViewModel

class RecipesListFragment : Fragment(), RecipeAdapter.OnRecipeClickListener {
    private lateinit var factory: RecipesViewModelFactory
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var viewModel: RecipesListViewModel
    private lateinit var loader: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recyclerview_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = RecipeRepository(RecipesApi())
        factory = RecipesViewModelFactory(repository)
        // Initialize ViewModel
        viewModel = ViewModelProvider(this, factory).get(RecipesListViewModel::class.java)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewRecipe)
        loader = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize and set adapter
        recipeAdapter = RecipeAdapter()
        recyclerView.adapter = recipeAdapter

        // Observe recipes LiveData from ViewModel
        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipeResponse ->
            recipeResponse?.let { // Check if recipeResponse is not null
                recipeAdapter.submitList(recipeResponse)
                loader.visibility = View.GONE
            }

        })
        // Fetch recipes
        viewModel.fetchRecipes("Cookie")
        recipeAdapter.setOnRecipeClickListener(this)

    }
    override fun onRecipeClick(recipe: Recipe) {
        // Handle recipe click here
        // For example, you can navigate to a detail fragment
        println("clickedd " + recipe.title)
        val action = RecipesListFragmentDirections.actionMusicalsListFragmentToMusicalFragment(recipe)
        Navigation.findNavController(requireView()).navigate(action)
    }
}
