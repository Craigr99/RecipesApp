package com.example.recipesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    //tell room what to do - insert or update existing recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntity) // insert recipe passing in a recipe entity

    // Insert multiple recipes using a list of recipe entities
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(recipes: List<RecipeEntity>)

    // Get all recipes ordering by date
    @Query("SELECT * FROM recipes ORDER BY date ASC")
    fun getAll(): LiveData<List<RecipeEntity>>

    // Get recipe by ID
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity?

    // Get number of rows
    @Query("SELECT COUNT(*) from recipes")
    fun getCount(): Int

    // Delete the selected recipes
    @Delete
    fun deleteRecipes(selectedRecipes: List<RecipeEntity>) : Int

    // Delete all recipes from db
    @Query("DELETE FROM recipes")
    fun deleteAll():Int

    // Delete a single recipe
    @Delete
    fun deleteRecipe(recipe: RecipeEntity)
}