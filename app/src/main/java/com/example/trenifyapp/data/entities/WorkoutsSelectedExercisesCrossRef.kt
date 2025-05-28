package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(tableName = "workout_selected_exercises_cross_ref",
    primaryKeys = ["workout_id", "selected_exercise_id"])
data class WorkoutsSelectedExercisesCrossRef (
    @ColumnInfo(name = "workout_id") val workoutId: Int,
    @ColumnInfo(name = "selected_exercise_id") val selectedExerciseId: Int,
)

data class WorkoutWithSelectedExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "selected_exercise_id",
        associateBy = Junction(WorkoutsSelectedExercisesCrossRef::class)
    )
    val selectedExercises: List<SelectedExercise>
)

data class SelectedExerciseWithWorkouts(
    @Embedded val selectedExercise: SelectedExercise,
    @Relation(
        parentColumn = "selected_exercise_id",
        entityColumn = "workout_id",
        associateBy = Junction(WorkoutsSelectedExercisesCrossRef::class)
    )
    val workouts: List<Workout>
)