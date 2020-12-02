package com.example.recipesapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.data.RecipeEntity
import com.example.recipesapp.databinding.ListItemBinding

class RecipesListAdapter(private val recipesList: List<RecipeEntity>) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }


}