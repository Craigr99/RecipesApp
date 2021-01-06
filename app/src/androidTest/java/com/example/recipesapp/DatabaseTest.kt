package com.example.recipesapp

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipesapp.data.AppDatabase
import com.example.recipesapp.data.RecipeDao
import com.example.recipesapp.data.SampleDataProvider
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: RecipeDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // init database
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        //init dao object
        dao = database.recipeDao()!!
    }

    @Test
    fun createRecipes() {
        dao.insertAll(SampleDataProvider.getRecipes())
        val count = dao.getCount()
        assertEquals(count, SampleDataProvider.getRecipes().size)
    }

    @After
    fun closeDb() {
        database.close()
    }
}