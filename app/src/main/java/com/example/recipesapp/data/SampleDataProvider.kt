package com.example.recipesapp.data

import java.util.*

class SampleDataProvider {

    companion object{
        private val sampleName1 = "Recipe Name 1"
        private val sampleName2 = "Recipe Name 2 a\nline feed"
        private val sampleName3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis dolor suscipit, feugiat enim a, placerat eros. Nulla feugiat risus sit amet quam elementum porttitor.
        """.trimIndent()

        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        fun getRecipes() = arrayListOf(
            RecipeEntity( sampleName1, "description1", "steps", 5, 8, getDate(0), 25),
            RecipeEntity( sampleName2, "description2", "steps", 4, 10, getDate(2), 55),
            RecipeEntity( sampleName3, "description3", "steps", 7, 5, getDate(3), 17),
            RecipeEntity( sampleName3, "description4", "steps", 7, 5, getDate(4), 17),
        )

    }
}