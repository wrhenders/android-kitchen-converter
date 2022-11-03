package com.kitchen.recipeconverter.ui.gramit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.databinding.FragmentGramItBinding


class GramItFragment : Fragment() {

    private var _binding: FragmentGramItBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GramItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGramItBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recipeText.setText(viewModel.rawRecipeString)

        binding.gramItButton.setOnClickListener {
            viewModel.parseRecipeString(binding.recipeText.text.toString())
            val action = GramItFragmentDirections.actionGramItFragmentToGramItEditFragment()
            findNavController().navigate(action)
        }
    }

}