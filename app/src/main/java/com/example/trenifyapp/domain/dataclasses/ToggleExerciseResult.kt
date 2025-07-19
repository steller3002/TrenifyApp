package com.example.trenifyapp.domain.dataclasses

import com.example.trenifyapp.presentation.dataclasses.ToggledExerciseInfo

data class ToggleExerciseResult (
    val toggledExercisesPerMuscleGroup: List<Int>,
    val toggledExercises: List<ToggledExerciseInfo>,
)