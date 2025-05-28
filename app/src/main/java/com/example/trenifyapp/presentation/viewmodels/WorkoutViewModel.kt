package com.example.trenifyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.data.entities.WorkoutExercise
import com.example.trenifyapp.data.relations.WorkoutWithWorkoutExercises
import com.example.trenifyapp.domain.usecases.GenerateWorkoutUseCase
import com.example.trenifyapp.presentation.dataclasses.WorkoutExerciseWithDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkoutViewModelState(
    val workout: Workout? = null,
    val workoutExercisesWithDetails: List<WorkoutExerciseWithDetails> = emptyList(),
    val phaseName: String = "Тренировка",
    val isLoading: Boolean = false
)

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val generateWorkoutUseCase: GenerateWorkoutUseCase,
    private val _appDb: AppDb
) : ViewModel() {
    var userId: Int = 0
    private var workoutId: Int = 0

    private val _state = MutableStateFlow(WorkoutViewModelState())
    val state = _state.asStateFlow()

    fun generateWorkoutAndLoadWorkoutExercises() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val workout = generateWorkoutUseCase.invoke(userId)
                workoutId = workout.workoutId ?: throw IllegalStateException("Workout ID is null")

                _state.update { it.copy(
                    phaseName = _appDb.phaseOfCycleDao.getById(workout.phaseOfCycleOwnerId).name
                ) }
                loadWorkoutExercises()
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun loadWorkoutExercises() {
        val workoutWithExercises = _appDb.workoutDao.getWorkoutWithWorkoutExercises(workoutId)
        _state.update { it ->
            it.copy(workoutExercisesWithDetails =
            workoutWithExercises.workoutExercises.map { loadWorkoutExerciseDetails(it) }
        ) }
    }

    private suspend fun loadWorkoutExerciseDetails(workoutExercise: WorkoutExercise): WorkoutExerciseWithDetails {
        val selectedExercise = _appDb.selectedExerciseDao.getByExerciseId(workoutExercise.selectedExerciseOwnerId)
        val exercise = _appDb.exerciseDao.getById(selectedExercise.exerciseOwnerId)

        return WorkoutExerciseWithDetails(
            workoutExercise = workoutExercise,
            exerciseName = exercise.name,
            exerciseWeight = selectedExercise.currentWorkingWeight,
            exerciseSets = selectedExercise.setsNumber,
            exerciseReps = selectedExercise.repeatsNumber
        )
    }
}