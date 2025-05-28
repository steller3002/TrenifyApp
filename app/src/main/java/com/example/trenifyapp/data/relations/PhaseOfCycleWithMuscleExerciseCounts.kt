package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.MuscleExerciseCount
import com.example.trenifyapp.data.entities.PhaseOfCycle

data class PhaseOfCycleWithMuscleExerciseCounts(
    @Embedded val phaseOfCycle: PhaseOfCycle,
    @Relation(
        parentColumn = "phase_of_cycle_id",
        entityColumn = "phase_of_cycle_owner_id"
    )
    val muscleExerciseCounts: List<MuscleExerciseCount>
)
