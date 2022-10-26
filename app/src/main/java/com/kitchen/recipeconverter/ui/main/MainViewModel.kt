package com.kitchen.recipeconverter.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.Ingredient

class MainViewModel : ViewModel() {
    val ingredientList: List<Ingredient> =
        listOf<Ingredient>(
        Ingredient("Flour", 120.0),
        Ingredient("Sugar", 201.0),
        Ingredient("Water", 236.0),
        Ingredient("Vegetable Oil", 225.0),
        Ingredient("Baking Powder", 192.0)
    )
    var selectedIngredient: Ingredient? = null
    var selectedConvertFrom: String? = null
    var selectedConvertTo: String? = null
    var entryAmount: String? = null

    private fun teaspoonToCups(quantity: Double): Double = quantity / 48
    private fun tablespoonToCups(quantity: Double): Double = quantity / 16

    private fun cupsToGrams(quantity: Double, weightConversion: Double): Double = quantity * weightConversion

    fun displayResults(): String {
        if(selectedIngredient != null &&
            selectedConvertFrom!= null &&
            selectedConvertTo!= null){
            val multiplier = conversionType(selectedConvertFrom!!, selectedConvertTo!!, selectedIngredient!!)
            return "${((entryAmount?.toDouble() ?: 1.0) * multiplier).toString()} $selectedConvertTo"
        } else {
            return ""
        }
    }

    private fun conversionType(convertFrom: String, convertTo: String, ingredient: Ingredient): Double {
        Log.d("Convert", "$convertFrom $convertTo")
        when (convertFrom) {
            "Cups"-> {
                when (convertTo) {
                    "Grams" -> return ingredient.weightConversion
                    "Teaspoons" -> return (1.0/48)
                    "Tablespoons" -> return (1.0/16)
                    "Cups" -> return 1.0
                }
            }
            "Teaspoons" -> {
                when (convertTo) {
                    "Grams" -> return ingredient.weightConversion/48
                    "Cups" -> return 48.0
                    "Tablespoons" -> return 3.0
                    "Teaspoons" -> return 1.0
                }
            }
            "Tablespoons" -> {
                when (convertTo) {
                    "Grams" -> return ingredient.weightConversion/16
                    "Cups" -> return 16.0
                    "Tablespoons" -> return 1.0
                    "Teaspoons" -> return (1.0/3)
                }
            }
            "Grams" -> {
                when (convertTo) {
                    "Grams" -> return 1.0
                    "Cups" -> return ingredient.weightConversion
                    "Tablespoons" -> return ingredient.weightConversion/16
                    "Teaspoons" -> return ingredient.weightConversion/48
                }
            }
        }
        return 0.0
    }
}