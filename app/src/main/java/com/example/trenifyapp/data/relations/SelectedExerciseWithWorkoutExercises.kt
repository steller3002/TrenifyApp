package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.WorkoutExercise

data class SelectedExerciseWithWorkoutExercises(
    @Embedded val selectedExercise: SelectedExercise,
    @Relation(
        parentColumn = "selected_exercise_id",
        entityColumn = "selected_exercise_owner_id"
    )
    val workoutExercises: List<WorkoutExercise>
)
