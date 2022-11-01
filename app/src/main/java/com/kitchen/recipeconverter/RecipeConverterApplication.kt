package com.kitchen.recipeconverter

import android.app.Application
import com.kitchen.recipeconverter.data.recipe.RecipeRoomDatabase

class RecipeConverterApplication : Application() {
    val database: RecipeRoomDatabase by lazy {RecipeRoomDatabase.getDatabase(this)}
}