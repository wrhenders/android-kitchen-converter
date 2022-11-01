package com.kitchen.recipeconverter.ui.recipelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.kitchen.recipeconverter.R
import com.kitchen.recipeconverter.ui.gramit.GramItViewModel


class EditRecipeFragment : Fragment() {
    private val viewModel: GramItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultString = viewModel.makeReturnString()
        val resultText = view.findViewById<EditText>(R.id.recipe_text)
        resultText.setText(resultString)
    }
}