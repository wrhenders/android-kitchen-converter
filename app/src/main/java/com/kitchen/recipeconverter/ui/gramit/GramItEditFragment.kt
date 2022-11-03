package com.kitchen.recipeconverter.ui.gramit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.databinding.FragmentGramItEditBinding


class GramItEditFragment : Fragment() {

    private var _binding: FragmentGramItEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: GramItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentGramItEditBinding.inflate(inflater,container,false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        val itemList = viewModel.itemList
        val ingredientList = viewModel.getIngredientListNames()

        val units = resources.getStringArray(R.array.unit_choices_short)
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
        val ingredientAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, ingredientList)
        val recyclerAdapter = GramItAdapter(itemList, menuAdapter, ingredientAdapter) { item, position ->
            viewModel.editList(position, item)
            Log.d("OutsideClick", "${viewModel.itemList[position]}")
        }
        binding.recyclerView.adapter = recyclerAdapter
        binding.floatingActionButton.setOnClickListener {
            viewModel.addEmptyItem()
            recyclerAdapter.notifyItemInserted(ingredientList.size-1)
        }
        binding.convertButton.setOnClickListener {
            val action = GramItEditFragmentDirections.actionGramItEditFragmentToEditRecipeFragment(getString(R.string.edit_recipe))
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}