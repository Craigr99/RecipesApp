package com.example.recipesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.data.RecipeEntity
import com.example.recipesapp.databinding.ListItemBinding

class RecipesListAdapter(
    private val recipesList: List<RecipeEntity>,
    private val listener: ListItemListener
) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    // selectedRecipes = array of recipe entities
    val selectedRecipes = arrayListOf<RecipeEntity>()

    val selectedRecipe = RecipeEntity()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            inflater.inflate(R.layout.list_item, parent, false) // inflate view with list item
        return ViewHolder(view)
    }

    // fun to get number of recipes
    override fun getItemCount() = recipesList.size

    // Display the data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipesList[position]
        with(holder.binding) {
            recipeText.text = recipe.name // set recipeText in list item to the actual text
            difficultyText.text = recipe.difficulty + "/10"
            qualityText.text = recipe.quality + "/10"

            root.setOnClickListener {
                // Notify listener class that user has clicked on this recipe
                // call editRecipe passing in recipe id
                listener.editRecipe(recipe.id)
            }
            // Set click listener for FAB
            fab.setOnClickListener {
                // if the recipe belongs to selectedRecipes array
                if (selectedRecipes.contains(recipe)) {
                    // delete recipe and reset the icon
                    selectedRecipes.remove(recipe)
                    fab.setImageResource(R.drawable.ic_recipe)
                } else {
                    // add recipe to array and change icon to checked
                    selectedRecipes.add(recipe)
                    fab.setImageResource(R.drawable.ic_check)
                }
                // notify fragment to rebuild menu
                listener.onItemSelectionChanged()
            }
            // Set icon img at runtime
            fab.setImageResource(
                // if recipe belongs to array, icon = checked. else it is default icon
                if (selectedRecipes.contains(recipe)) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_recipe
                }
            )
        }
    }

    //Handle click event
    // create interface
    interface ListItemListener {
        // call this from listener object - pass current recipe id
        fun editRecipe(recipeId: Int)

        // Notify fragment that something has been selected
        fun onItemSelectionChanged()
    }

}