package com.example.recipesapp

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipesapp.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {

    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var binding: EditorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Get reference to activity that owns this fragment
        (activity as AppCompatActivity).supportActionBar?.let {
            // if supportActionBar not null
            it.setHomeButtonEnabled(true)  // display home button
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back) // set to arrow icon
        }
        setHasOptionsMenu(true)

        // set title of activity
        requireActivity().title =
                // if recipe id = null
            if (args.recipeId == NEW_RECIPE_ID) {
                getString(R.string.new_recipe)
            } else {
                getString(R.string.edit_recipe)
            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)

        // initialize binding
        binding = EditorFragmentBinding.inflate(inflater, container, false)
        //update the text view
        binding.editName.setText("")

        // Handle back gestures from users
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }

        )

        // set up observer for the current recipe
        viewModel.currentRecipe.observe(viewLifecycleOwner, Observer {
            // get state values
            val savedString = savedInstanceState?.getString(RECIPE_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            //set recipe values:
            binding.editName.setText(savedString ?: it.name)
            binding.editDesc.setText(it.description)
            binding.editDifficulty.setText(it.difficulty)
            binding.editQuality.setText(it.quality)
            binding.editName.setSelection(cursorPosition)
        })
        // Go to view model and call getRecipeById() and pass in recipeId
        viewModel.getRecipeById(args.recipeId)

        return binding.root
    }

    //Handle home button selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Save data when user goes back
    private fun saveAndReturn(): Boolean {
        // close keyboard
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        // Get text value user has typed, then set the values
        viewModel.currentRecipe.value?.name = binding.editName.text.toString()
        viewModel.currentRecipe.value?.description = binding.editDesc.text.toString()
        viewModel.currentRecipe.value?.difficulty = binding.editDifficulty.text.toString()
        viewModel.currentRecipe.value?.quality = binding.editQuality.text.toString()
        // call updateRecipe() in view model
        viewModel.updateRecipe()

        findNavController().navigateUp() // Go back to main fragment
        return true
    }

    // For saving state
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.editName) {
            outState.putString(RECIPE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

}