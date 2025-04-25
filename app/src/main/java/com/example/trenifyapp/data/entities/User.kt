package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trenifyapp.domain.enums.Gender

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var username: String,
    var age: Int,
    var weight: Float,
    val gender: Gender,

    // Special for female:
    @ColumnInfo(name = "current_day_of_menstruation")
    var currentDayOfMenstruation: Int? = null,

    @ColumnInfo(name = "workout_program_id")
    var workoutProgramId: Int,
)
