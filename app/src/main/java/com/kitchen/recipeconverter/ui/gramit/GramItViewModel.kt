package com.kitchen.recipeconverter.ui.gramit

import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.Converter
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.Ingredient
import com.kitchen.recipeconverter.data.IngredientList

class GramItViewModel : ViewModel() {
    private val _itemList: MutableList<GramItItem> = mutableListOf(
        GramItItem("","","")
    )
    val itemList = _itemList

    private val ingredientList = IngredientList().getList()

    fun editList(position: Int, item: GramItItem) {
        if(validityCheck(item)) _itemList[position] = item
    }
    fun addItem() {
        itemList.add(GramItItem("","",""))
    }

    fun getIngredientListNames(): List<String>{
        return ingredientList.map { it.name }
    }

    private fun findIngredient(name: String?): Ingredient {
        return ingredientList.filter { it.name == name }[0]
    }

    fun makeReturnString():String{
        var returnString = ""
        for (item in _itemList){
            if (validityCheck(item)) {
                val unitType = Converter().getUnitType(item.unit)
                val quantity = convertToGramsMultiplier(unitType, findIngredient(item.ingredient))
                returnString += "$quantity g of ${item.ingredient} \n"
            }
        }
        return returnString
    }

    private fun validityCheck(item: GramItItem): Boolean {
        item.quantity?.toDoubleOrNull() ?: return false

        if (item.unit !in listOf("c", "T", "t", "oz")) return false

        if (item.ingredient !in getIngredientListNames()) return false

        return true
    }

    private fun convertToGramsMultiplier(unitType:String, ingredient: Ingredient):Double {
        return Converter().conversionType(unitType, "Grams", ingredient)
    }

}