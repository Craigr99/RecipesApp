package com.example.recipesapp

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

    //reference to adapter
    private lateinit var adapter: RecipesListAdapter


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
            binding.editSteps.setText(it.steps)
            binding.editTime.setText(it.time) 
            binding.editName.setSelection(cursorPosition)
        })
        // Go to view model and call getRecipeById() and pass in recipeId
        viewModel.getRecipeById(args.recipeId)

        return binding.root
    }

    // Init options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId =
            R.menu.menu_main_selected_items // use selected items menu (bin icon)
        // inflate the menu
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Handle home button selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            R.id.action_delete -> deleteRecipe()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteRecipe(): Boolean {
        // Go to view model and call deleteRecipe() passing in the selected recipe
        viewModel.deleteRecipe()
        // Show toast message
        Toast.makeText(
            getActivity(), "Recipe deleted!",
            Toast.LENGTH_LONG
        ).show();
        findNavController().navigateUp() // Go back to main fragment

        return true

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
        viewModel.currentRecipe.value?.steps = binding.editSteps.text.toString()
        viewModel.currentRecipe.value?.time = binding.editTime.text.toString()
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