package com.example.trenifyapp.data.dao

import androidx.room.Dao
import com.example.trenifyapp.data.entities.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    fun getAll(): Flow<List<Workout>>
}