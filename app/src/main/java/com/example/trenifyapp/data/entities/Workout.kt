package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_id")
    val workoutId: Int? = null,
    val date: Date,

    @ColumnInfo(name = "user_owner_id")
    val userOwnerId: Int,
    @ColumnInfo(name = "phase_of_cycle_owner_id")
    val phaseOfCycleOwnerId: Int,
)
