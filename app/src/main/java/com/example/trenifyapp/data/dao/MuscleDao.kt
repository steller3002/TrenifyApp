package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.trenifyapp.data.entities.Muscle
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleDao {
    @Query("SELECT * FROM muscles")
    fun getAll(): Flow<List<Muscle>>

    @Query("SELECT * FROM muscles WHERE muscle_id = :id")
    suspend fun getById(id: Int): Muscle
}