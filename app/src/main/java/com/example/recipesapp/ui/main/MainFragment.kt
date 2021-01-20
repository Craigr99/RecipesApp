package com.example.recipesapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.*
import com.example.recipesapp.data.RecipeEntity
import com.example.recipesapp.databinding.MainFragmentBinding

class MainFragment : Fragment(),
    //implement interface
    RecipesListAdapter.ListItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    //reference to adapter
    private lateinit var adapter: RecipesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // hide back button in main fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //Use options menu
        setHasOptionsMenu(true)

        // Set title of activity
        requireActivity().title = getString(R.string.app_name)

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.recipesList?.observe(viewLifecycleOwner, Observer {
            // initialize adapter
            adapter = RecipesListAdapter(
                it,
                this@MainFragment
            ) // Pass reference to the fragment as listener
            // pass adapter object to recycler view
            binding.recyclerView.adapter = adapter
            // tell recycler view if its a list or a grid
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)

            val selectedRecipes =
                savedInstanceState?.getParcelableArrayList<RecipeEntity>(SELECTED_RECIPES_KEY)
            adapter.selectedRecipes.addAll(selectedRecipes ?: emptyList())
        })

        // When user clicks FAB, call editRecipe()
        binding.floatingActionButton.setOnClickListener {
            editRecipe(NEW_RECIPE_ID)
        }

        return binding.root
    }

    // Init options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId =
            // If the adapter is initialized + selected recipes list is not empty
            if (this::adapter.isInitialized && adapter.selectedRecipes.isNotEmpty()) {
                R.menu.menu_main_selected_items // use selected items menu
            } else {
                R.menu.menu_main
            }

        // inflate the menu
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // When item is selected in the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Look for menu item -> run the function
            R.id.action_sample_data -> addSampleData()
            R.id.action_delete -> deleteSelectedRecipes()
            R.id.action_delete_all -> deleteAllRecipes()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllRecipes(): Boolean {
        // Go to view model and call deleteAllRecipes()
        viewModel.deleteAllRecipes()
        Toast.makeText(
            getActivity(), "Recipes deleted!",
            Toast.LENGTH_LONG
        ).show();
        return true
    }

    private fun deleteSelectedRecipes(): Boolean {
        // Go to view model and call deleteRecipes() passing in the selected recipes
        viewModel.deleteRecipes(adapter.selectedRecipes)
        // Reset the menu to original state
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedRecipes.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)

        // Toast message
        Toast.makeText(
            getActivity(), "Selected Recipes deleted!",
            Toast.LENGTH_LONG
        ).show();

        return true
    }

    private fun addSampleData(): Boolean {
        // Go to view model and call addSampleData()
        viewModel.addSampleData()

        // Toast message
        Toast.makeText(
            getActivity(), "Sample Recipes added!",
            Toast.LENGTH_LONG
        ).show();

        return true
    }

    override fun editRecipe(recipeId: Int) {
        // receive recipe id
        Log.i(TAG, "onItemClick: RECEIVED RECIPE ID $recipeId")
        // set action = actionEditRecipe and pass in recipe id
        val action = MainFragmentDirections.actionEditRecipe(recipeId)
        // get reference to nav host
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        // Reset options menu
        requireActivity().invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized) {
            outState.putParcelableArrayList(
                SELECTED_RECIPES_KEY,
                adapter.selectedRecipes
            )
        }
        super.onSaveInstanceState(outState)
    }

}