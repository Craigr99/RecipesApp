package com.example.recipesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.AppDatabase
import com.example.recipesapp.data.RecipeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(app: Application) : AndroidViewModel(app) {
    // Reference database
    private val database = AppDatabase.getInstance(app)

    // Reference the current selected recipe
    val currentRecipe = MutableLiveData<RecipeEntity>()

    // get recipe by ID
    fun getRecipeById(recipeId: Int) {
        // start background thread
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val recipe =
                    // if recipeId not null, call getRecipeById
                    if (recipeId != NEW_RECIPE_ID) {
                        database?.recipeDao()?.getRecipeById(recipeId)
                    } else {
                        // Create new instance of the recipe class
                        RecipeEntity()
                    }
                currentRecipe.postValue(recipe)
            }
        }
    }

    fun updateRecipe() {
        // use current recipe value
        currentRecipe.value?.let {
            //trim text values
            it.name = it.name.trim()
            it.description = it.description.trim()
            it.difficulty = it.difficulty.trim()
            it.quality = it.quality.trim()

            // If there is no recipe found, return
            if (it.id == NEW_RECIPE_ID && it.name.isEmpty() && it.description.isEmpty() && it.difficulty.isEmpty()  && it.quality.isEmpty()) {
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    // if all values are empty, delete recipe. else insert new recipe
                    if (it.name.isEmpty() && it.description.isEmpty() && it.difficulty.isEmpty() && it.quality.isEmpty()) {
                        database?.recipeDao()?.deleteRecipe(it)
                    } else {
                        database?.recipeDao()?.insertRecipe(it)
                    }
                }
            }
        }
    }
}