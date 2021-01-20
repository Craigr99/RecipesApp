package com.example.recipesapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.AppDatabase
import com.example.recipesapp.data.RecipeEntity
import com.example.recipesapp.data.SampleDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    // Reference to database
    private val database = AppDatabase.getInstance(app)
    // set recipesList to all recipes in the db using getAll()
    val recipesList = database?.recipeDao()?.getAll()

    fun addSampleData() {
        viewModelScope.launch {
            // run code in a background thread
            withContext(Dispatchers.IO) {
                //reference to sample recipes data
                val sampleRecipes = SampleDataProvider.getRecipes()
                //insert data to the database using insertAll() and passing in sampleRecipes
                database?.recipeDao()?.insertAll(sampleRecipes)
            }
        }
    }

    fun deleteRecipes(selectedRecipes: List<RecipeEntity>) {
        viewModelScope.launch {
            // run code in a background thread
            withContext(Dispatchers.IO) {
                // Delete the selected recipes passing selectedRecipes into deleteRecipes()
                database?.recipeDao()?.deleteRecipes(selectedRecipes)
            }
        }
    }

    fun deleteAllRecipes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Delete all the recipes
                database?.recipeDao()?.deleteAll()
            }
        }
    }
}