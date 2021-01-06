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

    fun getRecipeById(recipeId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val recipe =
                    if (recipeId != NEW_RECIPE_ID) {
                        database?.recipeDao()?.getRecipeById(recipeId)
                    } else {
                        // Create new instance of recipe class
                        RecipeEntity()
                    }
                currentRecipe.postValue(recipe)
            }
        }
    }

    fun updateRecipe() {
        // use current recipe name value
        currentRecipe.value?.let {
            //trim text value
            it.name = it.name.trim()
            if (it.id == NEW_RECIPE_ID && it.name.isEmpty()) {
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    // if name is empty, delete. else insert
                    if (it.name.isEmpty()) {
                        database?.recipeDao()?.deleteRecipe(it)
                    } else {
                        database?.recipeDao()?.insertRecipe(it)
                    }
                }
            }
        }
    }
}