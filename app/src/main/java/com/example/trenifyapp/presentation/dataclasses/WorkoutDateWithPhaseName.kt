package com.example.trenifyapp.presentation.dataclasses

import java.util.Date

data class WorkoutDateWithPhaseName(
    val workoutId: Long,
    val date: Date,
    val phaseName: String,
)
