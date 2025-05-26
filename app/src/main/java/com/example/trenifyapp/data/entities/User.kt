package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trenifyapp.domain.enums.Gender

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int? = null,
    var username: String,
    var age: Int,
    var weight: Float,
    val gender: Gender,

    @ColumnInfo(name = "workout_plan_owner_id")
    var workoutPlanOwnerId: Int,
)
