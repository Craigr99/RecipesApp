package com.example.recipesapp.data

import java.util.*

class SampleDataProvider {

    companion object{
        private val sampleName1 = "Lasagna"
        private val sampleName2 = "Burgers with \n fries"
        private val sampleName3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis dolor suscipit, feugiat enim a, placerat eros. Nulla feugiat risus sit amet quam elementum porttitor.
        """.trimIndent()

        private val sampleName4 = "Pasta"

        // Function to get date
        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        fun getRecipes() = arrayListOf(
            RecipeEntity( sampleName1, "This classic lasagna is made with an easy meat sauce as the base. Layer the sauce with noodles and cheese, then bake until bubbly! This is great for feeding a big family, and freezes well, too.", "steps", "5", "8", getDate(0), 25),
            RecipeEntity( sampleName2, "Want to add a spicy kick to your burgers? These Chipotle Burgers are made by mixing smoky chipotle peppers in adobo with the ground beef! Top with jack cheese and avocado for the best burger of the summer.", "steps", "4", "10", getDate(2), 55),
            RecipeEntity( sampleName3, "description3", "steps", "7", "5", getDate(3), 17),
            RecipeEntity( sampleName4, "This small-batch weeknight lasagna comfortably serves two and is ready in half the time as a larger batch. Loaf Pan Lasagna is layered with chunky pieces of sausage and rustic-but-quick tomato sauce.", "steps", "7", "5", getDate(4), 17),
        )

    }
}