package com.example.trenifyapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trenifyapp.data.dao.ExerciseDao
import com.example.trenifyapp.data.dao.MuscleDao
import com.example.trenifyapp.data.dao.MuscleGroupDao
import com.example.trenifyapp.data.dao.PhaseOfCycleDao
import com.example.trenifyapp.data.dao.SelectedExerciseDao
import com.example.trenifyapp.data.dao.UserDao
import com.example.trenifyapp.data.dao.WorkoutDao
import com.example.trenifyapp.data.dao.WorkoutExerciseDao
import com.example.trenifyapp.data.dao.WorkoutPlanDao
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.Muscle
import com.example.trenifyapp.data.entities.MuscleGroup
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.data.entities.ExercisesMusclesCrossRef
import com.example.trenifyapp.data.entities.MuscleExerciseCount
import com.example.trenifyapp.data.entities.PhaseOfCycle
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.data.entities.WorkoutExercise
import com.example.trenifyapp.data.entities.WorkoutsSelectedExercisesCrossRef

@Database(
    entities = [
        User::class,
        MuscleGroup::class,
        Muscle::class,
        WorkoutPlan::class,
        Workout::class,
        Exercise::class,
        SelectedExercise::class,
        PhaseOfCycle::class,
        WorkoutExercise::class,
        MuscleExerciseCount::class,
        ExercisesMusclesCrossRef::class,
        WorkoutsSelectedExercisesCrossRef::class,
    ],
    version = 1
)
@TypeConverters(
    DateConverter::class
)
abstract class AppDb : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val workoutPlanDao: WorkoutPlanDao
    abstract val exerciseDao: ExerciseDao
    abstract val muscleGroupDao: MuscleGroupDao
    abstract val selectedExerciseDao: SelectedExerciseDao
    abstract val workoutDao: WorkoutDao
    abstract val phaseOfCycleDao: PhaseOfCycleDao
    abstract val muscleDao: MuscleDao
    abstract val workoutExerciseDao: WorkoutExerciseDao
}