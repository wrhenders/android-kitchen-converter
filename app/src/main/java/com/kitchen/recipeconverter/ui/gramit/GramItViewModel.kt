package com.kitchen.recipeconverter.ui.gramit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.IngredientList

class GramItViewModel : ViewModel() {
    private val _itemList: MutableList<GramItItem> = mutableListOf(
        GramItItem("","","")
    )
    val itemList = _itemList

    fun editList(position: Int, item: GramItItem) {
        if(validityCheck(item)) _itemList[position] = item
    }
    fun addItem() {
        itemList.add(GramItItem("","",""))
    }

    fun getIngredientList(): List<String>{
        val ingredientList = IngredientList().getList()
        return ingredientList.map { it.name }
    }

    fun makeReturnString():String{
        var returnString = ""
        for (item in _itemList){
            if (validityCheck(item)) {
                returnString += "${item.quantity} ${item.unit} of ${item.ingredient} \n"
            }
        }
        return returnString
    }

    private fun validityCheck(item: GramItItem): Boolean {
        item.quantity?.toDoubleOrNull() ?: return false

        if (item.unit !in listOf("c", "T", "t", "oz")) return false

        val ingredientList = getIngredientList()
        if (item.ingredient !in ingredientList) return false

        return true
    }
}