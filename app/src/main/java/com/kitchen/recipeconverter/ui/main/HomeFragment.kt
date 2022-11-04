package com.kitchen.recipeconverter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.unitConverterCard.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUnitConverterFragment()
            findNavController().navigate(action)
        }
        binding.gramItCard.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGramItFragment()
            findNavController().navigate(action)
        }
        binding.recipeListCard.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment()
            findNavController().navigate(action)
        }
    }
}