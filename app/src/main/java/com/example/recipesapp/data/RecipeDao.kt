package com.example.recipesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    //tell room what to do - insert or update existing
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntity)

    // Insert multiple
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(recipes: List<RecipeEntity>)

    // Get all recipes
    @Query("SELECT * FROM recipes ORDER BY date ASC")
    fun getAll(): LiveData<List<RecipeEntity>>

    // Get recipe by ID
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity?

    // Get number of rows
    @Query("SELECT COUNT(*) from recipes")
    fun getCount(): Int

    // Delete recipes
    @Delete
    fun deleteRecipes(selectedRecipes: List<RecipeEntity>) : Int

    // Delete all recipes
    @Query("DELETE FROM recipes")
    fun deleteAll():Int

    @Delete
    fun deleteRecipe(recipe: RecipeEntity)
}