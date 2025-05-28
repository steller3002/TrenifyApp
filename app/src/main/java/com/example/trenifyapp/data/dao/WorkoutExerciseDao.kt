package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trenifyapp.data.entities.WorkoutExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {
    @Query("SELECT * FROM workout_exercises WHERE workout_owner_id = :workoutId")
    fun getAllByWorkoutId(workoutId: Int): Flow<List<WorkoutExercise>>

    @Insert
    suspend fun create(workoutExercise: WorkoutExercise)
}