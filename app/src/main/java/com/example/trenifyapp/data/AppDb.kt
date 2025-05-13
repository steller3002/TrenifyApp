package com.example.trenifyapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trenifyapp.data.dao.ExerciseDao
import com.example.trenifyapp.data.dao.UserDao
import com.example.trenifyapp.data.dao.WorkoutPlanDao
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.Muscle
import com.example.trenifyapp.data.entities.MuscleGroup
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.data.entities.ExercisesMusclesCrossRef

@Database(
    entities = [
        User::class,
        MuscleGroup::class,
        Muscle::class,
        WorkoutPlan::class,
        Exercise::class,
        SelectedExercise::class,
        ExercisesMusclesCrossRef::class,
    ],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val workoutPlanDao: WorkoutPlanDao
    abstract val exerciseDao: ExerciseDao
}