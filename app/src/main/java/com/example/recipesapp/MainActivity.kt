package com.example.recipesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipesapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // set view to main_activity
    }
}