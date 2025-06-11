package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.domain.dataclasses.ExerciseWithCharacteristics
import com.example.trenifyapp.domain.enums.Gender
import com.example.trenifyapp.domain.exceptions.CreateAccountException
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val _appDb: AppDb
) {
    suspend operator fun invoke(
        workoutId: Int,
        username: String,
        age: Int,
        weight: Float,
        gender: Gender,
        exercisesWithCharacteristics: List<ExerciseWithCharacteristics>
    ): Result<Long> {
        try {
            val phaseOfCycleId = _appDb.workoutPlanDao.getPhasesIds(workoutId)
                .phasesOfCycle[0].phaseOfCycleId
            if (phaseOfCycleId == null) return Result.failure(CreateAccountException())

            val user = User(
                userId = null,
                username = username,
                age = age,
                weight = weight,
                gender = gender,
                phaseOfCycleOwnerId = phaseOfCycleId,
                workoutPlanOwnerId = workoutId
            )
            val userId = _appDb.userDao.create(user)

            exercisesWithCharacteristics.forEach { exerciseWithCharacteristics ->
                val selectedExercise = SelectedExercise(
                    selectedExerciseId = null,
                    currentWorkingWeight = weight,
                    setsNumber = exerciseWithCharacteristics.characteristics.sets,
                    repeatsNumber = exerciseWithCharacteristics.characteristics.reps,
                    exerciseOwnerId = exerciseWithCharacteristics.exercise.exerciseId!!,
                    userOwnerId = userId.toInt()
                )

                _appDb.selectedExerciseDao.create(selectedExercise)
            }

            return Result.success(userId)
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }
}