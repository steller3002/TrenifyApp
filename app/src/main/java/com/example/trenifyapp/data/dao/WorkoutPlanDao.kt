package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.trenifyapp.data.entities.WorkoutPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {
    @Query("SELECT * FROM workout_plans")
    fun getAll(): Flow<List<WorkoutPlan>>

    @Query("SELECT * FROM workout_plans WHERE id = :id")
    suspend fun getById(id: Int): WorkoutPlan
}