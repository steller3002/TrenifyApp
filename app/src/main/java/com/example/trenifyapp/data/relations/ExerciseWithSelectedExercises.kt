package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.SelectedExercise

data class ExerciseWithSelectedExercises(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_owner_id")
    val selectedExercises: List<SelectedExercise>
)
