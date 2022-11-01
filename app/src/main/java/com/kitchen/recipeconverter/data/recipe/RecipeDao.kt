package com.kitchen.recipeconverter.data.recipe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)
    @Update
    suspend fun update(recipe: Recipe)
    @Delete
    suspend fun delete(recipe: Recipe)
    @Query("SELECT * from recipe WHERE id = :id")
    fun getRecipe(id: Int): Flow<Recipe>
    @Query("SELECT * FROM recipe ORDER BY title ASC")
    fun getRecipes(): Flow<List<Recipe>>
}