package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.MuscleGroup

data class MuscleGroupWithExercises(
    @Embedded val muscleGroup: MuscleGroup,
    @Relation(
        parentColumn = "id",
        entityColumn = "target_muscle_group_id"
    )
    val exercises: List<Exercise>
)
