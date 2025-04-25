package com.example.trenifyapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups")
data class MuscleGroup (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
)