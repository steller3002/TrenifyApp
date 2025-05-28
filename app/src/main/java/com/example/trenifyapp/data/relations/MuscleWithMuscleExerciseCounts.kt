package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.Muscle
import com.example.trenifyapp.data.entities.MuscleExerciseCount

data class MuscleWithMuscleExerciseCounts(
    @Embedded val muscle: Muscle,
    @Relation(
        parentColumn = "muscle_id",
        entityColumn = "muscle_owner_id"
    )
    val muscleExerciseCounts: List<MuscleExerciseCount>
)
