package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_exercises")
data class SelectedExercise(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "current_working_weight") var currentWorkingWeight: Float,
    @ColumnInfo(name = "sets_number") var setsNumber: Int,
    @ColumnInfo(name = "repeats_number") var repeatsNumber: Int,

    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
)
