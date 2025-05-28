package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.relations.UserWithSelectedExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE user_id = :id")
    suspend fun getById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(user: User): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(user: User)

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getSelectedExercises(userId: Int): UserWithSelectedExercises
}