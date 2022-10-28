package com.kitchen.recipeconverter.ui.gramit

import androidx.lifecycle.ViewModel
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.IngredientList

class GramItViewModel : ViewModel() {
    private val itemList: MutableList<GramItItem> = mutableListOf(
        GramItItem(0.0,"","")
    )
    fun getList(): MutableList<GramItItem> {
        return itemList
    }
    fun editList(position: Int, item: GramItItem) {
        itemList[position] = item
    }
    fun addItem() {
        itemList.add(GramItItem(0.0,"",""))
    }

    fun getIngredientList(): List<String>{
        val ingredientList = IngredientList().getList()
        return ingredientList.map {it -> it.name}
    }
}