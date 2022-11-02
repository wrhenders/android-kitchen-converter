package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.RecipeConverterApplication
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.databinding.FragmentRecipeDetailBinding
import com.kitchen.recipeconverter.databinding.FragmentRecipeListBinding


class RecipeDetailFragment : Fragment() {
    private val navigationArgs: RecipeDetailFragmentArgs by navArgs()

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var recipe: Recipe

    private val viewModel: RecipeListViewModel by activityViewModels {
        RecipeListViewModelFactory(
            (activity?.application as RecipeConverterApplication).database.recipeDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.recipeId
        viewModel.getRecipe(id).observe(this.viewLifecycleOwner) {selectedRecipe ->
            recipe = selectedRecipe
            bind(recipe)
        }
        binding.editButton.setOnClickListener {
            val action = RecipeDetailFragmentDirections
                .actionRecipeDetailFragmentToEditRecipeFragment(getString(R.string.edit_recipe), id)
            findNavController().navigate(action)
        }
    }

    private fun bind(recipe: Recipe) {
        binding.apply{
            titleText.setText(recipe.recipeTitle)
            titleText.isEnabled = false
            recipeText.setText(recipe.recipeIngredients)
            recipeText.isEnabled = false
            methodText.setText(recipe.recipeMethod)
            methodText.isEnabled = false
        }
    }

}