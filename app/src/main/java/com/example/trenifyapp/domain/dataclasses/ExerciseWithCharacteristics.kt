package com.example.trenifyapp.domain.dataclasses

import com.example.trenifyapp.data.entities.Exercise

data class ExerciseWithCharacteristics(
    val exercise: Exercise,
    val characteristics: ExerciseCharacteristics
)

data class ExerciseCharacteristics(
    val weight: Float,
    val sets: Int,
    val reps: Int,
)
