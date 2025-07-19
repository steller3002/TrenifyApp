package com.example.trenifyapp.domain.dataclasses

import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.WorkoutPlan

data class DataForRegistration(
    val workoutPlans: List<WorkoutPlan>,
    val muscleGroupNamesWithExercises: Map<String, List<Exercise>>,
    val toggledExercisesPerMuscleGroup: List<Int>
)