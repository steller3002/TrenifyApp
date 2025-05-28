package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phases_of_cycle")
data class PhaseOfCycle(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "phase_of_cycle_id")
    val phaseOfCycleId: Int?,
    val name: String,

    @ColumnInfo(name = "workout_plan_owner_id")
    val workoutPlanOwnerId: Int
)
