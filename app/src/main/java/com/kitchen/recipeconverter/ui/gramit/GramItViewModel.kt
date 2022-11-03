package com.kitchen.recipeconverter.ui.gramit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.Converter
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.Ingredient
import com.kitchen.recipeconverter.data.IngredientList
import java.text.DecimalFormat

class GramItViewModel : ViewModel() {
    private val _itemList: MutableList<GramItItem> = mutableListOf()
    val itemList get() = _itemList

    private val ingredientList = IngredientList().getList()


    private var _rawRecipeString: String = ""
    val rawRecipeString get() = _rawRecipeString

    fun editList(position: Int, item: GramItItem) {
        if(validityCheck(item)) _itemList[position] = item
    }
    fun addEmptyItem() {
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
        val df = DecimalFormat("#.##")
        Log.d("ParseReturn", "Item List: $itemList")
        for (item in _itemList){
            Log.d("ParseReturn", "$item")
            if (validityCheck(item)) {
                val unitType = Converter().getUnitType(item.unit)
                val quantity = convertToGramsMultiplier(unitType, findIngredient(item.ingredient))
                returnString += "${df.format(quantity)}g of ${item.ingredient} \n"
            }
        }
        Log.d("ParseReturn", "Returning:$returnString")
        return returnString.trim()
    }

    fun parseRecipeString(recipeText: String) {
        if(recipeText.isBlank()) return
        _rawRecipeString = recipeText
        val recipeLines = recipeText.split('\n')
        for ((i, line) in recipeLines.withIndex()){
            val items = line.split(' ')
            if(items.size < 3) {
                if(i in itemList.indices) {
                    _itemList[i] = GramItItem("","","")
                }
                continue
            }

            val quantity = items[0]
            val unit = items[1]
            val ingredient = items.slice(2 until items.size).joinToString(" ")

            val parsedQuantity = parseQuantity(quantity)
            val parsedUnit = parseUnit(unit)

            val parsedItem = GramItItem(parsedQuantity, parsedUnit, ingredient)
            if (i in itemList.indices) _itemList[i] = parsedItem
            else _itemList.add(parsedItem)
        }
    }

    private fun validityCheck(item: GramItItem): Boolean {
        item.quantity?.toDoubleOrNull() ?: return false

        if (Converter().getUnitType(item.unit).isEmpty()) return false

        if (item.ingredient !in getIngredientListNames()) return false

        return true
    }

    private fun parseQuantity(quantity: String) : String {
        quantity.toDoubleOrNull() ?: return ""
        return quantity
    }

    private fun parseUnit(unit: String) : String {
        if (Converter().getUnitType(unit).isEmpty()) return ""
        return unit
    }

    private fun parseIngredient(ingredient: String) : Boolean {
        if (ingredient !in getIngredientListNames()) return false
        return true
    }

    private fun convertToGramsMultiplier(unitType:String, ingredient: Ingredient):Double {
        return Converter().conversionType(unitType, "Grams", ingredient)
    }

}