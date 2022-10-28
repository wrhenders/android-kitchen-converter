package com.kitchen.recipeconverter.ui.gramit

import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.IngredientList

class GramItViewModel : ViewModel() {
    private val itemList: MutableList<GramItItem> = mutableListOf(
        GramItItem(null,"","")
    )
    fun getList(): MutableList<GramItItem> {
        return itemList
    }
    fun editList(position: Int, item: GramItItem) {
        itemList[position] = item
    }
    fun addToList(item: GramItItem) {
        itemList.add(item)
    }

    fun getIngredientList(): List<String>{
        val ingredientList = IngredientList().getList()
        return ingredientList.map {it -> it.name}
    }
}