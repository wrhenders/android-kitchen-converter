package com.kitchen.recipeconverter.ui.unit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.Ingredient
import com.kitchen.recipeconverter.data.IngredientList

class UnitConverterViewModel : ViewModel() {
    val ingredientList = IngredientList().getList()
    var selectedIngredient: Ingredient? = null
    var selectedConvertFrom: String? = null
    var selectedConvertTo: String? = null
    var entryAmount: String? = null

    fun displayResults(): String {
        if(selectedIngredient != null &&
            selectedConvertFrom!= null &&
            selectedConvertTo!= null){
            val multiplier = conversionType(selectedConvertFrom!!, selectedConvertTo!!, selectedIngredient!!)
            return "${((entryAmount?.toDouble() ?: 1.0) * multiplier).toString()}"
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
                    "Teaspoons" -> return 48.0
                    "Tablespoons" -> return 16.0
                    "Cups" -> return 1.0
                    "Ounces" -> return 8.0
                }
            }
            "Teaspoons" -> {
                when (convertTo) {
                    "Grams" -> return ingredient.weightConversion/48
                    "Cups" -> return (1.0/48)
                    "Tablespoons" -> return (1.0/3)
                    "Teaspoons" -> return 1.0
                    "Ounces" -> return (1.0/6)
                }
            }
            "Tablespoons" -> {
                when (convertTo) {
                    "Grams" -> return ingredient.weightConversion/16
                    "Cups" -> return (1.0/16)
                    "Tablespoons" -> return 1.0
                    "Teaspoons" -> return 3.0
                    "Ounces" -> return (1.0/2)
                }
            }
            "Ounces" -> {
                when (convertTo) {
                    "Grams" -> return ingredient.weightConversion/8
                    "Cups" -> return (1.0/8)
                    "Tablespoons" -> return 2.0
                    "Teaspoons" -> return 6.0
                    "Ounces" -> return 1.0
                }
            }
            "Grams" -> {
                when (convertTo) {
                    "Grams" -> return 1.0
                    "Cups" -> return 1/ingredient.weightConversion
                    "Ounces" -> return 1/(ingredient.weightConversion/8)
                    "Tablespoons" -> return 1/(ingredient.weightConversion/16)
                    "Teaspoons" -> return 1/(ingredient.weightConversion/48)
                }
            }
        }
        return 0.0
    }
}