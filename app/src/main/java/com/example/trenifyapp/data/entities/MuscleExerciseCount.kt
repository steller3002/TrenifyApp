package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_exercise_counts")
data class MuscleExerciseCount(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "muscle_exercise_count_id")
    val muscleExerciseCountId: Int?,
    @ColumnInfo(name = "exercises_by_muscle")
    val exercisesByMuscle: Int,

    @ColumnInfo(name = "phase_of_cycle_owner_id")
    val phaseOfCycleOwnerId: Int,
    @ColumnInfo(name = "muscle_owner_id")
    val muscleOwnerId: Int,
)
