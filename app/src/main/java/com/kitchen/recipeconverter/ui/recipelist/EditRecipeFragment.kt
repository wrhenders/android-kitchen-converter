package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.RecipeConverterApplication
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.databinding.FragmentEditRecipeBinding
import com.kitchen.recipeconverter.ui.gramit.GramItViewModel


class EditRecipeFragment : Fragment() {
    private val navigationArgs: EditRecipeFragmentArgs by navArgs()
    private var _binding: FragmentEditRecipeBinding? = null
    private val binding get() = _binding!!

    private val gramViewModel: GramItViewModel by activityViewModels()
    private val recipeViewModel: RecipeListViewModel by activityViewModels {
        RecipeListViewModelFactory(
            (activity?.application as RecipeConverterApplication).database.recipeDao()
        )
    }
    lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid() : Boolean {
        return recipeViewModel.isEntryValid(
            binding.titleText.text.toString(),
            binding.recipeText.text.toString(),
            binding.methodText.text.toString()
        )
    }

    private fun addNewRecipe() {
        if(isEntryValid()) {
            recipeViewModel.addNewRecipe(
                binding.titleText.text.toString(),
                binding.recipeText.text.toString(),
                binding.methodText.text.toString()
            )
            val action = EditRecipeFragmentDirections.actionEditRecipeFragmentToRecipeListFragment()
            findNavController().navigate(action)
        } else{
            showErrors()
        }
    }


    private fun updateRecipe() {
        if(isEntryValid()){
            recipeViewModel.updateRecipe(
                navigationArgs.recipeId,
                this.binding.titleText.text.toString(),
                this.binding.recipeText.text.toString(),
                this.binding.methodText.text.toString()
            )
            val action = EditRecipeFragmentDirections.actionEditRecipeFragmentToRecipeListFragment()
            findNavController().navigate(action)
        } else {
            showErrors()
        }
    }

    private fun showErrors(){
        if(this.binding.titleText.text.isNullOrEmpty()){
            this.binding.titleLabel.isErrorEnabled = true
            this.binding.titleLabel.error = "Add Title"
        } else {
            this.binding.titleLabel.isErrorEnabled = false
        }
        if(this.binding.recipeText.text.isNullOrEmpty()){
            this.binding.recipeLabel.isErrorEnabled = true
            this.binding.recipeLabel.error = "Add Recipe"
        } else {
            this.binding.recipeLabel.isErrorEnabled = false
        }
        if(this.binding.methodText.text.isNullOrEmpty()){
            this.binding.methodLabel.isErrorEnabled = true
            this.binding.methodLabel.error = "Add Method"
        } else {
            this.binding.methodLabel.isErrorEnabled = false
        }
    }

    private fun bind(recipe: Recipe) {
        binding.apply {
            titleText.setText(recipe.recipeTitle)
            recipeText.setText(recipe.recipeIngredients)
            methodText.setText(recipe.recipeMethod)
            saveButton.setOnClickListener { updateRecipe() }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.recipeId
        if (id>0) {
            recipeViewModel.getRecipe(id).observe(this.viewLifecycleOwner) { selectedRecipe ->
                recipe = selectedRecipe
                bind(recipe)
            }
        } else {
            if(navigationArgs.title == getString(R.string.edit_recipe)){
                val resultString = gramViewModel.makeReturnString()
                binding.recipeText.setText(resultString)
            }
            binding.saveButton.setOnClickListener {
                gramViewModel.clearItems()
                addNewRecipe()
            }
        }

    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}