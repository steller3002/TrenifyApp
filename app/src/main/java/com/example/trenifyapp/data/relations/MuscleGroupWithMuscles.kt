package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.Muscle
import com.example.trenifyapp.data.entities.MuscleGroup

data class MuscleGroupWithMuscles(
    @Embedded val muscleGroup: MuscleGroup,
    @Relation(
        parentColumn = "id",
        entityColumn = "muscleGroupId")
    val muscles: List<Muscle>
)
