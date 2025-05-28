package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.PhaseOfCycle
import com.example.trenifyapp.data.entities.WorkoutPlan

data class WorkoutPlanWithPhasesOfCycle(
    @Embedded val workoutPlan: WorkoutPlan,
    @Relation(
        parentColumn = "workout_plan_id",
        entityColumn = "workout_plan_owner_id"
    )
    val phasesOfCycle: List<PhaseOfCycle>
)
