package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.dataclasses.ToggleExerciseParams
import com.example.trenifyapp.domain.dataclasses.ToggleExerciseResult
import javax.inject.Inject

class ToggleExerciseUseCase @Inject constructor() {
    operator fun invoke(toggledExerciseParams: ToggleExerciseParams)
    : Result<ToggleExerciseResult> {
        val index = toggledExerciseParams.toggledExerciseInfo.muscleGroupId - 1
        if (index !in toggledExerciseParams.toggledExercisesPerMuscleGroup.indices) {
            return Result.failure(IndexOutOfBoundsException(index))
        }
        val currentExercisesNumber = toggledExerciseParams.toggledExercisesPerMuscleGroup[index]

        val updatedToggledExercisesPerMuscleGroup = toggledExerciseParams.toggledExercisesPerMuscleGroup.toMutableList()
        val updatedToggledExercises = toggledExerciseParams.toggledExercises.toMutableList()

        if (toggledExerciseParams.toggledExercises.contains(toggledExerciseParams.toggledExerciseInfo)) {
            updatedToggledExercisesPerMuscleGroup[index] = currentExercisesNumber - 1
            updatedToggledExercises.remove(toggledExerciseParams.toggledExerciseInfo)
        }
        else {
            updatedToggledExercisesPerMuscleGroup[index] = currentExercisesNumber + 1
            updatedToggledExercises.add(toggledExerciseParams.toggledExerciseInfo)
        }

        val result = ToggleExerciseResult(
            toggledExercises = updatedToggledExercises,
            toggledExercisesPerMuscleGroup = updatedToggledExercisesPerMuscleGroup
        )

        return Result.success(result)
    }
}