package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.data.relations.WorkoutWithWorkoutExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    /*TODO:
    выдача не всех сущностей, а постранично с подгрузкой
     */
    @Query("SELECT * FROM workouts WHERE workout_id = :workoutId")
    suspend fun getById(workoutId: Int): Workout

    @Query("SELECT * FROM workouts WHERE user_owner_id = :userId")
    fun getAllByUserId(userId: Int): Flow<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(workout: Workout): Long

    @Transaction
    @Query("SELECT * FROM workouts WHERE workout_id = :workoutId")
    suspend fun getWorkoutWithWorkoutExercises(workoutId: Int): WorkoutWithWorkoutExercises

    @Transaction
    @Query(
        "SELECT * FROM workouts WHERE user_owner_id = :userId AND phase_of_cycle_owner_id = :phaseOfCycleId " +
            "ORDER BY workout_id DESC LIMIT 1 OFFSET :offset")
    suspend fun getLastWorkoutWithWorkoutExercises(userId: Int, phaseOfCycleId: Int, offset: Int = 0):
            WorkoutWithWorkoutExercises?
}