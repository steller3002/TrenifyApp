package com.example.trenifyapp.domain.dataclasses

import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.data.relations.MuscleGroupWithExercises

data class DataForRegistration(
    val workoutPlans: List<WorkoutPlan>,
    val muscleGroupNamesWithExercises: Map<String, List<Exercise>>
)