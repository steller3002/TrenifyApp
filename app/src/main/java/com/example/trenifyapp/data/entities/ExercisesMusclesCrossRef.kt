package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(tableName = "exercises_muscles_cross_ref",
    primaryKeys = ["exercise_id", "muscle_id"])
data class ExercisesMusclesCrossRef(
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "muscle_id") val muscleId: Int,
)

data class ExerciseWithMuscles(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "muscle_id",
        associateBy = Junction(ExercisesMusclesCrossRef::class))
    val muscles: List<Muscle>
)

data class MuscleWithExercises(
    @Embedded val muscle: Muscle,
    @Relation(
        parentColumn = "muscle_id",
        entityColumn = "exercise_id",
        associateBy = Junction(ExercisesMusclesCrossRef::class))
    val exercises: List<Exercise>
)