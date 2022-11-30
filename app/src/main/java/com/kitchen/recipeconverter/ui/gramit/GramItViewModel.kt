package com.kitchen.recipeconverter.ui.gramit

import android.text.SpannableString
import android.text.style.UnderlineSpan
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

    private val converter = Converter()

    private var _rawRecipeString: String = ""
    val rawRecipeString get() = _rawRecipeString

    fun editList(position: Int, item: GramItItem) {
        _itemList[position] = item
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

    fun clearItems() {
        itemList.clear()
        _rawRecipeString = ""
    }

    fun makeReturnString():String{
        var returnString = ""
        val df = DecimalFormat("#.##")
        for (item in _itemList){
            Log.d("Parser", "Item: $item")
            returnString += if (validityCheck(item)) {
                val unitType = converter.getUnitType(item.unit)
                val quantity = convertToGramsMultiplier(unitType, findIngredient(item.ingredient))
                "${df.format(quantity)} g ${item.ingredient} \n"
            } else {
                val quantity = item.quantity?.toDoubleOrNull() ?: ""
                val builtString = if (quantity == "") {
                    "? ${item.unit} ${item.ingredient} \n"
                } else {
                    "${df.format(quantity)} ${item.unit} ${item.ingredient} \n"
                }

                builtString
            }
        }
        return returnString.trim()
    }

    fun parseRecipeString(recipeText: String) {
        if(recipeText.isBlank()) return
        _rawRecipeString = recipeText
        val recipeLines = recipeText.lines().filter { it.isNotEmpty() }
        for ((i, line) in recipeLines.withIndex()){
            val items = line.split("\\s".toRegex())
            var parsedItem = GramItItem("","","")
            if(items.size < 2) {
                if(i in itemList.indices) {
                    _itemList[i] = parsedItem
                }
                continue
            }
            val quantity = items[0]
            var parsedQuantity = parseQuantity(quantity)
            parsedItem = if(items.size == 2) {
                GramItItem(parsedQuantity, "", items[1])
            } else {
                var unitLocation = 1
                if (converter.convertToDecimal(items[unitLocation]) > 0) {
                    parsedQuantity = (parsedQuantity.toDouble() + parseQuantity(items[unitLocation]).toDouble()).toString()
                    unitLocation += 1
                }
                val unit = items[unitLocation]
                val parsedUnit = parseUnit(unit)
                Log.d("Parse", "items: $items")
                val ingredient = items.slice(unitLocation + 1 until items.size).joinToString(" ")
                GramItItem(parsedQuantity, parsedUnit, ingredient)
            }
            if (i in itemList.indices) _itemList[i] = parsedItem
            else _itemList.add(parsedItem)
        }
        Log.d("Parser", "Out: $_itemList")
    }

    private fun validityCheck(item: GramItItem): Boolean {
        item.quantity?.toDoubleOrNull() ?: return false

        if (converter.getUnitType(item.unit).isEmpty()) return false

        if (item.ingredient !in getIngredientListNames()) return false

        return true
    }

    private fun parseQuantity(quantity: String) : String {
        val parsedQuantity = if (quantity.toDoubleOrNull() == null) {
            val divideChar = quantity.indexOf("/")
            val numbers = quantity.map { num ->
                converter.convertToDecimal(num.toString())
            }
            if (divideChar > 0) {
                val fraction = numbers.slice(0 until divideChar).sum() / numbers.slice(divideChar + 1..numbers.lastIndex).sum()
                fraction.toString()
            } else {
                numbers.sum().toString()
            }
        } else {
            quantity
        }
        return parsedQuantity
    }

    private fun parseUnit(unit: String) : String {
        val parsedUnit = converter.getGramItUnit(unit)
        if (parsedUnit.isEmpty()) return unit
        return parsedUnit
    }

    private fun parseIngredient(ingredient: String) : Boolean {
        if (ingredient !in getIngredientListNames()) return false
        return true
    }

    private fun convertToGramsMultiplier(unitType:String, ingredient: Ingredient):Double {
        return Converter().conversionType(unitType, "Grams", ingredient)
    }

}