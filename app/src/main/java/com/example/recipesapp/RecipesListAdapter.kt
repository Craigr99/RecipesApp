package com.example.recipesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.data.RecipeEntity
import com.example.recipesapp.databinding.ListItemBinding

class RecipesListAdapter(private val recipesList: List<RecipeEntity>,
 private val listener: ListItemListener) :

    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = recipesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe  = recipesList[position]
        with(holder.binding) {
            recipeText.text = recipe.name
            root.setOnClickListener{
                // Notify listener class that user has clicked on this note
                listener.onItemClick(recipe.id)
            }
        }
    }

    //Handle click event
    // create interface
    interface ListItemListener {
        // call this from listener object - pass current recipe id
        fun onItemClick(recipeId: Int)
    }

}