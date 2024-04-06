package com.example.finalproject.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.MusicalRecyclerAdapter
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentMusicalsListBinding
import com.example.finalproject.model.Recipe
import com.example.finalproject.repositories.RecipeRepository
import com.example.finalproject.viewModels.RecipesListViewModel
import java.util.*

class RecipesListFragment : Fragment() {
    private var data: List<Recipe> = LinkedList()
    private var adapter: MusicalRecyclerAdapter? = null
    private var binding: FragmentMusicalsListBinding? = null
    private lateinit var viewModel: RecipesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_musicals_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val repository = RecipeRepository()
//        viewModel = ViewModelProvider(this, RecipeViewModelFactory(repository)).get(RecipeViewModel::class.java)
//
//        setupRecyclerView()
//
//        viewModel.recipes.observe(viewLifecycleOwner, Observer {
//            recipeAdapter.submitList(it)
//        })

//        searchButton.setOnClickListener {
//            val query = searchEditText.text.toString().trim()
//            if (query.isNotEmpty()) {
//                viewModel.fetchRecipes(query)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    fun reloadData() {
        binding!!.progressBar.visibility = View.VISIBLE
    }
}