package com.kitchen.recipeconverter.data.recipe

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val recipeTitle: String,
    @ColumnInfo(name = "ingredients")
    val recipeIngredients: String,
    @ColumnInfo(name = "method")
    val recipeMethod: String
)