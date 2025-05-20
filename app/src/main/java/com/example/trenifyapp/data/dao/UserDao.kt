package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trenifyapp.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(user: User): Long
}