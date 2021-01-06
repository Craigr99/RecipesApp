package com.example.recipesapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.RecipesListAdapter
import com.example.recipesapp.TAG
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
        })

        return binding.root
    }

    // Init options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId =
            // If the adapter is initialized + selected notes list is not empty
            if (this::adapter.isInitialized && adapter.selectedRecipes.isNotEmpty()) {
                R.menu.menu_main_selected_items
            } else {
                R.menu.menu_main
            }

        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Look for menu item
            R.id.action_sample_data -> addSampleData()
            R.id.action_delete -> deleteSelectedRecipes()
            R.id.action_delete_all -> deleteAllRecipes()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllRecipes(): Boolean {
        viewModel.deleteAllRecipes()
        return true
    }

    private fun deleteSelectedRecipes(): Boolean {
        viewModel.deleteRecipes(adapter.selectedRecipes)
        // Reset the menu to original state
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedRecipes.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)
        return true
    }

    private fun addSampleData(): Boolean {
        viewModel.addSampleData()
        return true
    }

    override fun onItemClick(recipeId: Int) {
        // receive recipe id
        Log.i(TAG, "onItemClick: RECEIVED RECIPE ID $recipeId")
        val action = MainFragmentDirections.actionEditRecipe(recipeId)
        // get reference to nav host
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        // Reset options menu
        requireActivity().invalidateOptionsMenu()
    }

}