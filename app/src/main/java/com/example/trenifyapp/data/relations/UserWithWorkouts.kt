package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.entities.Workout

data class UserWithWorkouts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_owner_id"
    )
    val workouts: List<Workout>
)