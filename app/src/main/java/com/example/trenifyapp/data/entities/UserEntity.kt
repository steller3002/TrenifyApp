package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var username: String,
    var age: Int,
    var weight: Float,
    val gender: String,

    // Special for female:
    var currentDayOfMenstruation: Int? = null,

    var workoutProgramId: Int,
)