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
    val recipesList = database?.recipeDao()?.getAll()

    fun addSampleData() {
        viewModelScope.launch {
            // run code in a background thread
            withContext(Dispatchers.IO) {
                //reference to sample data
                val sampleRecipes = SampleDataProvider.getRecipes()
                //insert data to database
                database?.recipeDao()?.insertAll(sampleRecipes)
            }
        }
    }

    fun deleteRecipes(selectedRecipes: List<RecipeEntity>) {
        viewModelScope.launch {
            // run code in a background thread
            withContext(Dispatchers.IO) {
                // Delete selected recipes
                database?.recipeDao()?.deleteRecipes(selectedRecipes)
            }
        }
    }

    fun deleteAllRecipes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.recipeDao()?.deleteAll()
            }
        }
    }
}