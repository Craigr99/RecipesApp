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
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }
        setHasOptionsMenu(true)

        // set title of activity
        requireActivity().title =
            if (args.recipeId == NEW_RECIPE_ID) {
                getString(R.string.new_recipe)
            } else {
                getString(R.string.edit_recipe)
            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)

        // initialize binding
        binding = EditorFragmentBinding.inflate(inflater, container, false)
        //update the text view
        binding.editor.setText("")

        // Handle back gestures from users
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }

        )

        viewModel.currentRecipe.observe(viewLifecycleOwner, Observer {
            binding.editor.setText(it.name)
        })
        viewModel.getRecipeById(args.recipeId)

        return binding.root
    }

    //Handle home button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAndReturn(): Boolean {
        // close keyboard
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        // Get text value user has typed
        viewModel.currentRecipe.value?.name = binding.editor.text.toString()
        viewModel.updateRecipe()

        findNavController().navigateUp() // Go back to main fragment
        return true
    }

}