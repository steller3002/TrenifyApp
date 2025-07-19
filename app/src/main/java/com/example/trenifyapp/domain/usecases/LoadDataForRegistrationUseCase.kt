package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.domain.dataclasses.DataForRegistration
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoadDataForRegistrationUseCase @Inject constructor(
    private val _appDb: AppDb
) {
    suspend operator fun invoke(): Result<DataForRegistration> {
        try {
            val workoutPlans = _appDb.workoutPlanDao.getAll().first()
            val muscleGroupsAndExercises = _appDb.muscleGroupDao.getAllWithExercises().first()

            val muscleGroupNamesWithExercises: MutableMap<String, List<Exercise>> = mutableMapOf()
            val toggledExercisesNumbers: MutableList<Int> = mutableListOf()

            muscleGroupsAndExercises.forEach {
                muscleGroupNamesWithExercises[it.muscleGroup.name] = it.exercises
                toggledExercisesNumbers.add(0)
            }

            val result = DataForRegistration(
                workoutPlans = workoutPlans,
                muscleGroupNamesWithExercises = muscleGroupNamesWithExercises,
                toggledExercisesPerMuscleGroup = toggledExercisesNumbers,
            )

            return Result.success(result)
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }
}