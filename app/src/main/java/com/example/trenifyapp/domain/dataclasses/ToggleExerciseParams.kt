package com.example.trenifyapp.domain.dataclasses

import com.example.trenifyapp.presentation.dataclasses.ToggledExerciseInfo

data class ToggleExerciseParams(
    val toggledExercisesPerMuscleGroup: List<Int>,
    val toggledExerciseInfo: ToggledExerciseInfo,
    val toggledExercises: List<ToggledExerciseInfo>
)
