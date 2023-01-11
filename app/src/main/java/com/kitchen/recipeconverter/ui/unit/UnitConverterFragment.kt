package com.kitchen.recipeconverter.ui.unit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.Ingredient
import com.kitchen.recipeconverter.data.hideKeyboard
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.databinding.FragmentUnitConverterBinding


class UnitConverterFragment : Fragment() {

    private var _binding: FragmentUnitConverterBinding? = null
    private val binding get() = _binding!!
    private lateinit var ingredientAdapter: IngredientAdapter

    private val viewModel: UnitConverterViewModel by activityViewModels()
    private var ingredientList = listOf<Ingredient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnitConverterBinding.inflate(inflater,container,false)
        ingredientList = viewModel.ingredientList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeReturnText(){
            viewModel.entryAmount = binding.topQuantityText.text.toString()
            binding.bottomQuantityText.setText(viewModel.displayResults(), TextView.BufferType.SPANNABLE)
        }

        fun filter(text: String) {
            val searchTerm = text.lowercase()
            val filteredList = mutableListOf<Ingredient>()
            for (ingredient in ingredientList) {
                if (ingredient.name.lowercase().contains(searchTerm)) filteredList.add(ingredient)
            }
            if (filteredList.isEmpty()) {
                ingredientAdapter.submitList(ingredientList)
            } else {
                ingredientAdapter.submitList(filteredList)
            }

        }

        val units = resources.getStringArray(R.array.unit_choices)
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)

         ingredientAdapter = IngredientAdapter {
            viewModel.selectedIngredient = it
            makeReturnText()
        }
        binding.recyclerView.adapter = ingredientAdapter
        ingredientAdapter.submitList(ingredientList)

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?):Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return false
            }
        })

        binding.topAutoCompleteTextView.setAdapter(menuAdapter)
        binding.bottomAutoCompleteTextView.setAdapter(menuAdapter)

        binding.topAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
             viewModel.selectedConvertFrom = binding.topAutoCompleteTextView.text.toString()
            makeReturnText()
            hideKeyboard()
        }
        binding.bottomAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            viewModel.selectedConvertTo = binding.bottomAutoCompleteTextView.text.toString()
            makeReturnText()
            hideKeyboard()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}