package com.kitchen.recipeconverter.ui.gramit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.databinding.FragmentGramItBinding
import com.kitchen.recipeconverter.databinding.FragmentMainBinding
import com.kitchen.recipeconverter.ui.unit.IngredientAdapter
import com.kitchen.recipeconverter.ui.unit.UnitConverterViewModel

class GramItFragment : Fragment() {

    private var _binding: FragmentGramItBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: GramItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentGramItBinding.inflate(inflater,container,false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        val itemList = viewModel.getList()
        val ingredientList = viewModel.getIngredientList()

        val units = resources.getStringArray(R.array.unit_choices_short)
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
        val ingredientAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, ingredientList)
        binding.recyclerView.adapter = GramItAdapter(itemList, menuAdapter, ingredientAdapter)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}