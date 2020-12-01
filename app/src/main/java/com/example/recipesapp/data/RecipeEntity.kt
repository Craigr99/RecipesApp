package com.example.recipesapp.data

import com.example.recipesapp.NEW_NOTE_ID
import java.util.*

data class RecipeEntity(
    var id: Int,
    var name: String,
    var description: String,
    var steps: String,
    var difficulty: Int,
    var quality: Int,
    var date: Date,
    var time: Int
) {
    constructor() : this(NEW_NOTE_ID, "", "", "", 0, 0, Date(), 0)
    constructor(
        name: String,
        description: String,
        steps: String,
        difficulty: Int,
        quality: Int,
        date: Date,
        time: Int
    ) : this(NEW_NOTE_ID, name, description, steps, difficulty, quality, date, time)
}