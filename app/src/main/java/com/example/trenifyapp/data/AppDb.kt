package com.example.trenifyapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trenifyapp.data.dao.UserDao
import com.example.trenifyapp.data.entities.MuscleGroup
import com.example.trenifyapp.data.entities.User

@Database(
    entities = [
        User::class,
        MuscleGroup::class,
    ],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract val usersDao: UserDao
}