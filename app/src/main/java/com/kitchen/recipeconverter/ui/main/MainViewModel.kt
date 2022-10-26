package com.kitchen.recipeconverter.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private fun teaspoonToCups(quantity: Double): Double = quantity / 48
    private fun tablespoonToCups(quantity: Double): Double = quantity / 16

    private fun cupsToGrams(quantity: Double, weightConversion: Double): Double = quantity * weightConversion
}