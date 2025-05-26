package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_plans")
data class WorkoutPlan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_plan_id")
    val workoutPlanId: Int? = null,
    val name: String,
    val description: String,
    @ColumnInfo(name = "days_per_cycle")
    val daysPerCycle: Int,

    /*TODO
    Наименование тренировочных фаз (например тренировка Верха или Низа, Ноги или Спина)
     */
)
