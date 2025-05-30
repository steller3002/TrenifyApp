package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.User

data class UserWithSelectedExercises(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_owner_id")
    val selectedExercises: List<SelectedExercise>
)
