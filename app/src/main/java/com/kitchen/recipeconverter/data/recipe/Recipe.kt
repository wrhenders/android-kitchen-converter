package com.kitchen.recipeconverter.data.recipe

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val recipeTitle: String,
    @ColumnInfo(name = "ingredients")
    val recipeIngredients: String,
    @ColumnInfo(name = "method")
    val recipeMethod: String,
    @ColumnInfo(name = "date")
    val dateModified: Long
)

fun Recipe.getFormattedDate(): String = SimpleDateFormat("MMM d, yyyy").format(Date(dateModified).time)