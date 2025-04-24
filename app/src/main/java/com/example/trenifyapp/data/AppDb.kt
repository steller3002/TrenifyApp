package com.example.trenifyapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trenifyapp.data.dao.UserEntityDao
import com.example.trenifyapp.data.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract val usersDao: UserEntityDao
}