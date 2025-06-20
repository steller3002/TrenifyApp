package com.example.trenifyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.domain.dataclasses.ExerciseCharacteristics
import com.example.trenifyapp.domain.dataclasses.ExerciseWithCharacteristics
import com.example.trenifyapp.domain.enums.Gender
import com.example.trenifyapp.domain.usecases.CreateAccountUseCase
import com.example.trenifyapp.presentation.dataclasses.SelectedExerciseWithName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val _appDb: AppDb,
    private val _createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    init {
        loadWorkoutPlans()
        loadMuscleGroupsAndExercises()
    }

    private var _state = MutableStateFlow(SignUpScreenState())
    val state = _state.asStateFlow()

    //region Методы обновления UI
    fun updateUsername(text: String) {
        _state.update { it.copy(username = text) }
    }

    fun updateAge(text: String) {
        _state.update { it.copy(age = text) }
    }

    fun updateWeight(text: String) {
        _state.update { it.copy(weight = text) }
    }

    fun changeGender(gender: Gender) {
        _state.update { it.copy(gender = gender) }
    }

    fun changeWorkoutId(workoutId: Int) {
        _state.update { it.copy(workoutId = workoutId) }
    }

    fun toggleExerciseSelection(exerciseId: Int, muscleGroupName: String) {
        val currentExercisesNumber =
            _state.value.muscleGroupNamesWithExerciseNumbers[muscleGroupName]!!

        if (_state.value.toggledExerciseIds.contains(exerciseId)) {
            _state.update {
                it.copy(
                    toggledExerciseIds = it.toggledExerciseIds.minus(exerciseId),
                    muscleGroupNamesWithExerciseNumbers = it.muscleGroupNamesWithExerciseNumbers
                        .toMutableMap().apply { this[muscleGroupName] = currentExercisesNumber - 1 }
                )
            }
        } else {
            _state.update {
                it.copy(
                    toggledExerciseIds = it.toggledExerciseIds.plus(exerciseId),
                    muscleGroupNamesWithExerciseNumbers = it.muscleGroupNamesWithExerciseNumbers
                        .toMutableMap().apply { this[muscleGroupName] = currentExercisesNumber + 1 }
                )
            }
        }
    }
    //endregion

    //region Методы загрузки данных
    private fun loadWorkoutPlans() {
        viewModelScope.launch {
            val workoutPlans = _appDb.workoutPlanDao.getAll().first()
            _state.update { it.copy(workoutPlans = workoutPlans) }
        }
    }

    private fun loadMuscleGroupsAndExercises() {
        viewModelScope.launch {
            val muscleGroupsWithExercises = _appDb.muscleGroupDao.getAllWithExercises().first()
            val muscleGroupNamesWithExercises = mutableMapOf<String, List<Exercise>>()
            val muscleGroupIdsWithExercisesNumber = mutableMapOf<String, Int>()

            muscleGroupsWithExercises.forEach { groupWithExercise ->
                muscleGroupNamesWithExercises[groupWithExercise.muscleGroup.name] =
                    groupWithExercise.exercises
                muscleGroupIdsWithExercisesNumber[groupWithExercise.muscleGroup.name] = 0
            }

            _state.update {
                it.copy(
                    muscleGroupNamesWithExercises = muscleGroupNamesWithExercises,
                    muscleGroupNamesWithExerciseNumbers = muscleGroupIdsWithExercisesNumber
                )
            }
        }
    }
    //endregion

    //region Методы валидации
    private fun validateUsername() {
        _state.update {
            it.copy(
                fieldsErrorState = if (it.username.trim().isBlank()) {
                    it.fieldsErrorState.copy(username = Constants.REQUIRED_FIELD_MESSAGE)
                } else {
                    it.fieldsErrorState.copy(username = "")
                }
            )
        }
    }

    private fun validateAge() {
        _state.update {
            it.copy(
                fieldsErrorState = when {
                    _state.value.age.trim()
                        .isBlank() -> it.fieldsErrorState.copy(age = Constants.REQUIRED_FIELD_MESSAGE)

                    else -> {
                        try {
                            if (it.age.trim().toInt() !in Constants.MIN_AGE..Constants.MAX_AGE)
                                it.fieldsErrorState.copy(age = Constants.WRONG_AGE_MESSAGE)
                            else it.fieldsErrorState.copy(age = "")
                        } catch (_: Exception) {
                            it.fieldsErrorState.copy(age = Constants.INVALID_FIELD_VALUE_MESSAGE)
                        }
                    }
                }
            )
        }
    }

    private fun validateWeight() {
        _state.update {
            it.copy(
                fieldsErrorState = when {
                    _state.value.weight.trim()
                        .isBlank() -> it.fieldsErrorState.copy(weight = Constants.REQUIRED_FIELD_MESSAGE)

                    else -> {
                        try {
                            if (it.weight.trim()
                                    .toFloat() !in Constants.MIN_WEIGHT..Constants.MAX_WEIGHT
                            ) {
                                it.fieldsErrorState.copy(weight = Constants.WRONG_WEIGHT_MESSAGE)
                            } else it.fieldsErrorState.copy(weight = "")
                        } catch (_: Exception) {
                            it.fieldsErrorState.copy(weight = Constants.INVALID_FIELD_VALUE_MESSAGE)
                        }
                    }
                }
            )
        }
    }

    fun fieldsIsValid(): Boolean {
        validateUsername()
        validateAge()
        validateWeight()

        if (_state.value.fieldsErrorState.isValid()) return true
        return false
    }

    fun checkNumberOfExercises() {
        if (_state.value.muscleGroupNamesWithExerciseNumbers.any { it.value < Constants.MIN_EXERCISES_NUMBER }) {
            _state.update {
                it.copy(
                    fieldsErrorState = it.fieldsErrorState.copy(
                        numberOfExercises = Constants.NOT_ENOUGH_EXERCISE_MESSAGE
                    )
                )
            }
            return
        }

        _state.update { it.copy(fieldsErrorState = it.fieldsErrorState.copy(numberOfExercises = "")) }
    }
    //endregion

    //region Основная логика
    fun createAccount() {
        viewModelScope.launch {
//            try {
//                val exerciseWithCharacteristics = _state.value.toggledExerciseIds.map { id ->
//                    val exercise = _appDb.exerciseDao.getById(id)
//                    ExerciseWithCharacteristics(
//                        exercise = exercise,
//                        characteristics = ExerciseCharacteristics(
//
//                        )
//                    )
//                }
//
//                _createAccountUseCase.invoke(
//                    workoutId = _state.value.workoutId!!,
//                    username = _state.value.username,
//                    age = _state.value.age.toInt(),
//                    weight = _state.value.weight.toFloat(),
//                    gender = _state.value.gender,
//                    exercisesWithCharacteristics =
//                )
//            }
//        }
        }
        //endregion
    }
}


data class SignUpScreenState(
    val username: String = "",
    val age: String = "",
    val weight: String = "",
    val gender: Gender = Gender.Male,
    val workoutId: Int? = null,
    val workoutPlans: List<WorkoutPlan> = listOf(),
    val muscleGroupNamesWithExercises: MutableMap<String, List<Exercise>> = mutableMapOf(),
    val muscleGroupNamesWithExerciseNumbers: MutableMap<String, Int> = mutableMapOf(),
    val toggledExerciseIds: Set<Int> = setOf(),
    val selectedExercises: List<SelectedExercise> = listOf(),
    val fieldsErrorState: ErrorsState = ErrorsState(),
)

data class ErrorsState(
    var username: String = "",
    var age: String = "",
    var weight: String = "",
    var numberOfExercises: String = ""
) {
    fun isValid(): Boolean {
        return username.isEmpty() && age.isEmpty() && weight.isEmpty()
    }
}

object Constants {
    val MIN_AGE = 10
    val MAX_AGE = 120
    val MIN_WEIGHT = 20f
    val MAX_WEIGHT = 400f
    val MIN_EXERCISES_NUMBER = 2
    val REQUIRED_FIELD_MESSAGE = "Обязательное поле"
    val WRONG_AGE_MESSAGE = "Значение должно быть больше $MIN_AGE и меньше $MAX_AGE"
    val WRONG_WEIGHT_MESSAGE = "Значение должно быть больше $MIN_WEIGHT и меньше $MAX_WEIGHT"
    val INVALID_FIELD_VALUE_MESSAGE = "Некорректное значение"
    val NOT_ENOUGH_EXERCISE_MESSAGE = "Необходимо выбрать хотя бы $MIN_EXERCISES_NUMBER упражнения"
    val INVALID_FIELDS_DATA_MESSAGE = "Некорректные данные для создания аккаунта"
    val GENDER_VARIANTS = listOf(Gender.Male, Gender.Female)
}