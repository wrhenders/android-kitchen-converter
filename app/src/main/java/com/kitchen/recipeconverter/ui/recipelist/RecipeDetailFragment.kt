package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
    lateinit var watchedRecipe: LiveData<Recipe>

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
        watchedRecipe = viewModel.getRecipe(id)
        watchedRecipe.observe(this.viewLifecycleOwner) { selectedRecipe ->
            recipe = selectedRecipe
            bind(recipe)
        }
    }

    private fun scaleRecipe(recipe: Recipe, value: Float) {
        val updatedText  = viewModel.scaleRecipe(recipe, value)
        this.binding.recipeText.setText(updatedText)
        if (updatedText.contains("**Edit**")) {
            this.binding.recipeLabel.isErrorEnabled = true
            this.binding.recipeLabel.error = "**Edit Quantity to Scale**"
        } else {
            this.binding.recipeLabel.isErrorEnabled = false
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
            scaleSlider.addOnChangeListener { _, value, _ ->
                scaleRecipe(recipe, value)
            }
            editButton.setOnClickListener {
                    val action = RecipeDetailFragmentDirections
                        .actionRecipeDetailFragmentToEditRecipeFragment(getString(R.string.edit_recipe), recipe.id)
                    findNavController().navigate(action)
                }
            deleteButton.setOnClickListener { showConfirmationDialog() }
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(android.R.string.dialog_alert_title)
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No"){_,_->}
            .setPositiveButton("Yes") {_,_->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        watchedRecipe.removeObservers(this.viewLifecycleOwner)
        viewModel.deleteRecipe(recipe)
        val action = RecipeDetailFragmentDirections.actionRecipeDetailFragmentToRecipeListFragment()
        findNavController().navigate(action)
    }


    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}