package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trenifyapp.data.entities.SelectedExercise

@Dao
interface SelectedExerciseDao {
    @Query("SELECT * FROM selected_exercises WHERE selected_exercise_id = :exerciseId")
    suspend fun getByExerciseId(exerciseId: Int): SelectedExercise

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(selectedExercise: SelectedExercise)
}