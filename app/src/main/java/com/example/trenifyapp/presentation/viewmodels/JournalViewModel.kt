package com.example.trenifyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.presentation.dataclasses.WorkoutDateWithPhaseName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val _appDb: AppDb
) : ViewModel() {
    private val _state = MutableStateFlow(JournalViewModelState())
    val state = _state.asStateFlow()

    fun getWorkoutDatesWithPhaseNames(userId: Int) {
        viewModelScope.launch {
            _appDb.workoutDao.getAllByUserId(userId).collect { workouts ->
                val workoutDatesWithPhaseNames = mutableListOf<WorkoutDateWithPhaseName>()
                workouts.forEach { workout ->
                    val phaseName = _appDb.phaseOfCycleDao.getById(workout.phaseOfCycleOwnerId).name

                    workoutDatesWithPhaseNames.add(WorkoutDateWithPhaseName(
                        workoutId = workout.workoutId!!.toLong(),
                        phaseName = phaseName,
                        date = workout.date
                    ))
                }

                _state.update { it.copy(
                    workoutDatesWithPhaseNames = workoutDatesWithPhaseNames.reversed()
                ) }
            }
        }
    }
}

data class JournalViewModelState(
    val workoutDatesWithPhaseNames: List<WorkoutDateWithPhaseName> = emptyList()
)