package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kitchen.recipeconverter.RecipeConverterApplication
import com.kitchen.recipeconverter.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

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
        val adapter = RecipeAdapter()
        binding.recyclerRecipeList.adapter = adapter
//       viewModel.addNewRecipe("Mac n Cheese", "3 g Mac\n3g Cheese", "Make it!")
        viewModel.allRecipes.observe(this.viewLifecycleOwner) { recipes ->
            recipes.let {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}