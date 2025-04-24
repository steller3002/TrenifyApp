package com.example.trenifyapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.trenifyapp.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserEntityDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>
}