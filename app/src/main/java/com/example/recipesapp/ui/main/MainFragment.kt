package com.example.recipesapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.recipesList.observe(viewLifecycleOwner, Observer {
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

    override fun onItemClick(recipeId: Int) {
        // receive recipe id
        Log.i(TAG, "onItemClick: RECEIVED RECIPE ID $recipeId")
        val action = MainFragmentDirections.actionEditRecipe(recipeId)
        // get reference to nav host
        findNavController().navigate(action)
    }

}