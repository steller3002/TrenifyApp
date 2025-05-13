package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.entities.WorkoutPlan

data class WorkoutPlanWithUsers(
    @Embedded val workoutPlan: WorkoutPlan,
    @Relation(
        parentColumn = "id",
        entityColumn = "workout_program_id"
    )
    val users: List<User>
)
