package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kitchen.recipeconverter.R
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
        val adapter = RecipeAdapter {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerRecipeList.adapter = adapter
//       viewModel.addNewRecipe("Mac n Cheese", "3 g Mac\n3g Cheese", "Make it!")
        viewModel.allRecipes.observe(this.viewLifecycleOwner) { recipes ->
            recipes.let {
                adapter.submitList(it)
            }
        }
        binding.floatingButton.setOnClickListener {
            val action = RecipeListFragmentDirections
                .actionRecipeListFragmentToEditRecipeFragment(getString(R.string.add_recipe))
            this.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}