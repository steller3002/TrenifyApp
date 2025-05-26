package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Int? = null,
    val name: String,
    val description: String,

    @ColumnInfo(name = "muscle_group_owner_id")
    val muscleGroupOwnerId: Int
)
