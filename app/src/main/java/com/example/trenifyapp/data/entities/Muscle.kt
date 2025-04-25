package com.example.trenifyapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscles")
data class Muscle(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,

    @ColumnInfo(name = "muscle_group_id") val muscleGroupId: Int
)
