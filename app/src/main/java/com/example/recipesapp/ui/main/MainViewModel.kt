package com.example.recipesapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesapp.data.RecipeEntity
import com.example.recipesapp.data.SampleDataProvider

class MainViewModel : ViewModel() {

    val recipesList = MutableLiveData<List<RecipeEntity>>()

    init {
        recipesList.value = SampleDataProvider.getRecipes()
    }
}