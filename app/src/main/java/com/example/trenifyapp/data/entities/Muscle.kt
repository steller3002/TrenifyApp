package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscles")
data class Muscle(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "muscle_id")
    val muscleId: Int? = null,
    val name: String,

    @ColumnInfo(name = "muscle_group_owner_id")
    val muscleGroupOwnerId: Int
)
