package com.example.recipesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.NEW_RECIPE_ID
import com.example.recipesapp.NEW_RECIPE_ID
import java.util.*

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var steps: String,
    var difficulty: Int,
    var quality: Int,
    var date: Date,
    var time: Int
) {
    constructor() : this(NEW_RECIPE_ID, "", "", "", 0, 0, Date(), 0)
    constructor(
        name: String,
        description: String,
        steps: String,
        difficulty: Int,
        quality: Int,
        date: Date,
        time: Int
    ) : this(NEW_RECIPE_ID, name, description, steps, difficulty, quality, date, time)
}