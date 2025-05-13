package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.ExerciseWithMuscles
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE exercise_id = :id")
    suspend fun getById(id: Int): Exercise

    @Transaction
    @Query("SELECT * FROM exercises")
    fun getAllWithMuscles(): Flow<List<ExerciseWithMuscles>>
}