package com.kitchen.recipeconverter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel

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
        var topUnit = ""
        var bottomUnit = ""
        var ingredient = ""

        fun makeReturnText(){
            binding.resultText.text = "From "+ topUnit +" To "+ bottomUnit
        }

        val ingredients = listOf<String>("Flour", "Sugar", "Vegetable Oil", "Salt")
        val units = resources.getStringArray(R.array.unit_choices)
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = IngredientAdapter(ingredients)
        binding.topAutoCompleteTextView.setAdapter(menuAdapter)
        binding.bottomAutoCompleteTextView.setAdapter(menuAdapter)

//        binding.topAutoCompleteTextView.setOnItemClickListener
        binding.topAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
             topUnit = binding.topAutoCompleteTextView.text.toString()
            makeReturnText()
        }
        binding.bottomAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            bottomUnit = binding.bottomAutoCompleteTextView.text.toString()
            makeReturnText()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}