package com.example.recipesapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.NEW_RECIPE_ID
import com.example.recipesapp.NEW_RECIPE_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "recipes") // table name
// set up recipe class
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var steps: String,
    var difficulty: String,
    var quality: String,
    var date: Date,
    var time: String,
) : Parcelable {
    constructor() : this(NEW_RECIPE_ID, "", "", "", "", "",  Date(), "")
    constructor(
        name: String,
        description: String,
        steps: String,
        difficulty: String,
        quality: String,
        date: Date,
        time: String
    ) : this(NEW_RECIPE_ID, name, description, steps, difficulty, quality, date, time)
}