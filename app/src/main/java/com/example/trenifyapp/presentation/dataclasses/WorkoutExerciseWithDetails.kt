package com.example.trenifyapp.presentation.dataclasses

import com.example.trenifyapp.data.entities.WorkoutExercise

data class WorkoutExerciseWithDetails(
    val workoutExercise: WorkoutExercise,
    val exerciseName: String,
    val exerciseWeight: Float,
    val exerciseSets: Int,
    val exerciseReps: Int,
)
