package com.kitchen.recipeconverter.ui.recipelist

import android.util.Log
import androidx.lifecycle.*
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.data.recipe.RecipeDao
import kotlinx.coroutines.launch

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