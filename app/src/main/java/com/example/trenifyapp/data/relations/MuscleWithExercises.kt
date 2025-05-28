package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.Muscle

data class MuscleWithExercises(
    @Embedded val muscle: Muscle,
    @Relation(
        parentColumn = "muscle_id",
        entityColumn = "muscle_owner_id"
    )
    val exercises: List<Exercise>
)
