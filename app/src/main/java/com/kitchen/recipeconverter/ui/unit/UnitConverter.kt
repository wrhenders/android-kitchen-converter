package com.kitchen.recipeconverter.ui.unit

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
import com.kitchen.recipeconverter.databinding.FragmentMainBinding

class UnitConverter : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: UnitConverterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentMainBinding.inflate(inflater,container,false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun makeReturnText(){
            viewModel.entryAmount = binding.topQuantityText.text.toString()
            binding.bottomQuantityText.setText(viewModel.displayResults(), TextView.BufferType.SPANNABLE)
        }

        val units = resources.getStringArray(R.array.unit_choices)
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = IngredientAdapter(viewModel.ingredientList) {
            viewModel.selectedIngredient = it
            makeReturnText()
        }
        binding.topAutoCompleteTextView.setAdapter(menuAdapter)
        binding.bottomAutoCompleteTextView.setAdapter(menuAdapter)

        binding.topAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
             viewModel.selectedConvertFrom = binding.topAutoCompleteTextView.text.toString()
            makeReturnText()
        }
        binding.bottomAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            viewModel.selectedConvertTo = binding.bottomAutoCompleteTextView.text.toString()
            makeReturnText()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}