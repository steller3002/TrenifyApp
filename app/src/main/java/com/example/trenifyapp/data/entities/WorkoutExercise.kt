package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercises")
data class WorkoutExercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_exercise_id")
    val workoutExerciseId: Int? = null,

    @ColumnInfo(name = "selected_exercise_owner_id")
    val selectedExerciseOwnerId: Int,
    @ColumnInfo(name = "workout_owner_id")
    val workoutOwnerId: Int,
)