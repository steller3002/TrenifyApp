package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_exercises")
data class SelectedExercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "selected_exercise_id")
    val selectedExerciseId: Int? = null,
    @ColumnInfo(name = "current_working_weight") var currentWorkingWeight: Float,
    @ColumnInfo(name = "sets_number") var setsNumber: Int,
    @ColumnInfo(name = "repeats_number") var repeatsNumber: Int,

    @ColumnInfo(name = "exercise_owner_id") val exerciseOwnerId: Int,
    @ColumnInfo(name = "user_owner_id") var userOwnerId: Int,
)
