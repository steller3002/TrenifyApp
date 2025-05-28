package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.trenifyapp.data.entities.PhaseOfCycle
import com.example.trenifyapp.data.relations.PhaseOfCycleWithMuscleExerciseCounts

@Dao
interface PhaseOfCycleDao {
    @Transaction
    @Query("SELECT * FROM phases_of_cycle WHERE phase_of_cycle_id = :phaseOfCycleId")
    suspend fun getWithMuscleExerciseCountsById(phaseOfCycleId: Int): PhaseOfCycleWithMuscleExerciseCounts

    @Query("SELECT * FROM phases_of_cycle WHERE phase_of_cycle_id = :phaseOfCycleId")
    suspend fun getById(phaseOfCycleId: Int): PhaseOfCycle
}