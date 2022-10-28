package com.kitchen.recipeconverter.ui.gramit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kitchen.recipeconverter.R

class GramItFragment : Fragment() {

    companion object {
        fun newInstance() = GramItFragment()
    }

    private lateinit var viewModel: GramItViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gram_it, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GramItViewModel::class.java)
        // TODO: Use the ViewModel
    }

}