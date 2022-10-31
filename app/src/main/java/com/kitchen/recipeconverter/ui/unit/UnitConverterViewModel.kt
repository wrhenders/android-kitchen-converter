package com.kitchen.recipeconverter.ui.unit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.Converter
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
            val multiplier = Converter().conversionType(selectedConvertFrom!!, selectedConvertTo!!, selectedIngredient!!)
            return ((entryAmount?.toDouble() ?: 1.0) * multiplier).toString()
        } else {
            return ""
        }
    }


}