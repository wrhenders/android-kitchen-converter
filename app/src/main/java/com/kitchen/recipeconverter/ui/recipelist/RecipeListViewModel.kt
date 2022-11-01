package com.kitchen.recipeconverter.ui.recipelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.kitchen.recipeconverter.data.recipe.Recipe
import com.kitchen.recipeconverter.data.recipe.RecipeDao

class RecipeListViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getRecipes().asLiveData()
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