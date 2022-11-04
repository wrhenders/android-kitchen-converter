package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.BoolRes
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.room.Query
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.RecipeConverterApplication
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private var recipeList = listOf<Recipe>()
    lateinit var adapter: RecipeAdapter

    private val viewModel: RecipeListViewModel by activityViewModels {
        RecipeListViewModelFactory(
            (activity?.application as RecipeConverterApplication).database.recipeDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeAdapter {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerRecipeList.adapter = adapter

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?):Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return false
            }
        })

        viewModel.allRecipes.observe(this.viewLifecycleOwner) { recipes ->
            recipes.let {
                adapter.submitList(it)
                recipeList = it
            }
        }

        binding.floatingButton.setOnClickListener {
            val action = RecipeListFragmentDirections
                .actionRecipeListFragmentToEditRecipeFragment(getString(R.string.add_recipe))
            this.findNavController().navigate(action)
        }
    }

    private fun filter(text: String) {
        val searchTerm = text.lowercase()
        val filteredList = mutableListOf<Recipe>()
        for (recipe in recipeList) {
            if (recipe.recipeTitle.lowercase().contains(searchTerm)) filteredList.add(recipe)
        }
        if (filteredList.isEmpty()) {
            adapter.submitList(recipeList)
        } else {
            adapter.submitList(filteredList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}