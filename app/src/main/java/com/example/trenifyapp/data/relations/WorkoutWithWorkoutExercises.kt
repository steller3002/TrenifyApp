package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.data.entities.WorkoutExercise

data class WorkoutWithWorkoutExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "workout_owner_id"
    )
    val workoutExercises: List<WorkoutExercise>
)
