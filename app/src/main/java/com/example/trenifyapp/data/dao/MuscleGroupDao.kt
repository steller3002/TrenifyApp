package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.trenifyapp.data.entities.MuscleGroup
import com.example.trenifyapp.data.relations.MuscleGroupWithExercises
import com.example.trenifyapp.data.relations.MuscleGroupWithMuscles
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleGroupDao {
    @Query("SELECT * FROM muscle_groups")
    fun getAll(): Flow<List<MuscleGroup>>

    @Transaction
    @Query("SELECT * FROM muscle_groups")
    fun getAllWithMuscles(): Flow<List<MuscleGroupWithMuscles>>

    @Transaction
    @Query("SELECT * FROM muscle_groups")
    fun getAllWithExercises(): Flow<List<MuscleGroupWithExercises>>

    @Query("SELECT * FROM muscle_groups WHERE muscle_group_id = :id")
    suspend fun getById(id: Int): MuscleGroup
}