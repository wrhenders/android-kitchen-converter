package com.kitchen.recipeconverter.ui.unit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.data.hideKeyboard
import com.kitchen.recipeconverter.databinding.FragmentUnitConverterBinding


class UnitConverterFragment : Fragment() {

    private var _binding: FragmentUnitConverterBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: UnitConverterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnitConverterBinding.inflate(inflater,container,false)
        return binding.root
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

        binding.recyclerView.adapter = IngredientAdapter(viewModel.ingredientList) {
            viewModel.selectedIngredient = it
            makeReturnText()
        }
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