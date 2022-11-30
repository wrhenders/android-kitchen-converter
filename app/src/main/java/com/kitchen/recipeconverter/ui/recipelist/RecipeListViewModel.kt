package com.kitchen.recipeconverter.ui.recipelist

import android.util.Log
import androidx.lifecycle.*
import com.kitchen.recipeconverter.data.GramItItem
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.data.recipe.RecipeDao
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class RecipeListViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getRecipes().asLiveData()

    private fun insertRecipe(recipe: Recipe){
        viewModelScope.launch {
            Log.d("DB", "Launching")
            recipeDao.insert(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch {
            recipeDao.delete(recipe)
        }
    }

    private fun updateRecipe(recipe: Recipe){
        viewModelScope.launch {
            recipeDao.update(recipe)
        }
    }

    fun addNewRecipe(recipeTitle: String, recipeIngredients: String, recipeMethod: String) {
        val currentDate = System.currentTimeMillis()
        val newRecipe = Recipe(recipeTitle=recipeTitle, recipeIngredients = recipeIngredients, recipeMethod = recipeMethod, dateModified = currentDate)
        Log.d("DB", "Adding $newRecipe")
        insertRecipe(newRecipe)
    }

    fun updateRecipe(id: Int, recipeTitle: String, recipeIngredients: String, recipeMethod: String) {
        val currentDate = System.currentTimeMillis()
        val updatedRecipe = Recipe(id, recipeTitle, recipeIngredients, recipeMethod, dateModified = currentDate)
        updateRecipe(updatedRecipe)
    }

    fun scaleRecipe(recipe: Recipe, scaleRatio: Float): String {
        val recipeText = recipe.recipeIngredients
        if(recipeText.isNullOrBlank()) return ""
        var updatedString = ""
        val recipeLines = recipeText.split('\n')
        for (line in recipeLines){
            val items = line.split(' ')
            if(items.size < 2 || items[0].toFloatOrNull() == null || scaleRatio.isNaN()) {
                updatedString += "$line **Edit**\n"
                continue
            }
            val quantity = items[0]
            val updatedValue = DecimalFormat("#.##").format(quantity.toFloat() * scaleRatio)
            updatedString += if(items.size == 2) {
                "$updatedValue ${items[1]}\n"
            } else {
                val unit = items[1]
                val ingredient = items.slice(2 until items.size).joinToString(" ")
                "$updatedValue $unit $ingredient\n"
            }
        }
        return updatedString.trim()
    }

    fun getRecipe(id: Int): LiveData<Recipe> {
        return recipeDao.getRecipe(id).asLiveData()
    }

    fun isEntryValid(recipeTitle: String, recipeIngredients: String, recipeMethod: String) : Boolean {
        if (recipeTitle.isBlank() || recipeIngredients.isBlank() || recipeMethod.isBlank()){
            return false
        }
        return true
    }

}

class RecipeListViewModelFactory(private val recipeDao: RecipeDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeListViewModel(recipeDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}