package com.example.trenifyapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.User

data class UserWithSelectedExercises(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId")
    val selectedExercises: List<SelectedExercise>
)
