package com.example.trenifyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.entities.WorkoutPlan
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
        loadExercises()
    }

    private var _state = MutableStateFlow(SignUpScreenState())
    val state = _state.asStateFlow()

    //region Методы обновления UI
    fun updateUsername(text: String) { _state.update { it.copy(username = text.trim()) } }
    fun updateAge(text: String) { _state.update { it.copy(age = text.trim()) } }
    fun updateWeight(text: String) { _state.update { it.copy(weight = text.trim()) } }
    fun changeGender(gender: Gender) { _state.update { it.copy(gender = gender) } }
    fun changeWorkoutId(workoutId: Int) { _state.update { it.copy(workoutId = workoutId) } }

    fun toggleExerciseSelection(exerciseId: Int) {
        if (_state.value.toggledExerciseIds.contains(exerciseId)) {
            _state.update { it.copy(toggledExerciseIds = it.toggledExerciseIds.minus(exerciseId)) }
        } else {
            _state.update { it.copy(toggledExerciseIds = it.toggledExerciseIds.plus(exerciseId)) }
        }
    }
    //endregion

    //region Методы загрузки данных
    private fun loadWorkoutPlans() {
        viewModelScope.launch {
            _appDb.workoutPlanDao.getAll().collect { plans ->
                _state.update { it.copy(workoutPlans = plans) }
            }
        }
    }

    private fun loadMuscleGroupsAndExercises() {
        viewModelScope.launch {
            val muscleGroupsWithExercises = _appDb.muscleGroupDao.getAllWithExercises().first()
            val localMap = mutableMapOf<String, List<Exercise>>()

            muscleGroupsWithExercises.forEach { groupWithExercise ->
                localMap[groupWithExercise.muscleGroup.name] = groupWithExercise.exercises
            }

            _state.update { it.copy(muscleGroupWithExercisesMap = localMap) }
        }
    }

    private fun loadExercises() {
        viewModelScope.launch {
            _appDb.exerciseDao.getAll().collect { list ->
                _state.update { it.copy(exercises = list) }
            }
        }
    }
    //endregion

    //region Методы валидации
    private fun validateUsername() {
        _state.update {
            it.copy(
                fieldsErrorState = if (_state.value.username.isBlank()) {
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
                    _state.value.age.isBlank() -> it.fieldsErrorState.copy(age = Constants.REQUIRED_FIELD_MESSAGE)
                    else -> {
                        try {
                            if (_state.value.age.toInt() !in Constants.MIN_AGE..Constants.MAX_AGE)
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
                    _state.value.weight.isBlank() -> it.fieldsErrorState.copy(weight = Constants.REQUIRED_FIELD_MESSAGE)
                    else -> {
                        try {
                            if (it.weight.toFloat() !in Constants.MIN_WEIGHT..Constants.MAX_WEIGHT) {
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

    private fun workoutIdExists(): Boolean {
        return _state.value.workoutPlans.any { it.workoutPlanId == _state.value.workoutId }
    }

    fun fieldsIsValid(): Boolean {
        validateUsername()
        validateAge()
        validateWeight()

        if (_state.value.fieldsErrorState.isValid()) {
            return true
        }

        _state.update { it.copy(signUpState = SignUpState.Error(Constants.INVALID_FIELDS_DATA_MESSAGE)) }
        return false
    }
    //endregion

    //region Основная логика
    fun createAccount() {
        viewModelScope.launch {
//            _createAccountUseCase.invoke(
//                workoutId = _state.value.workoutId!!,
//                username = _state.value.username,
//                age = _state.value.age.toInt(),
//                weight = _state.value.weight.toFloat(),
//                gender = _state.value.gender,
//                exercisesWithCharacteristics =
//            )
        }
    }

    fun createSelectedExercisesWithNames() {
        viewModelScope.launch {
            try {
                val selectedExercisesWithNames = _state.value.toggledExerciseIds.map { id ->
                    SelectedExerciseWithName(
                        SelectedExercise(
                        selectedExerciseId = null,
                        exerciseOwnerId = id,
                        repeatsNumber = 8,
                        userOwnerId = -1,
                        setsNumber = 3,
                        currentWorkingWeight = 20f),
                        exerciseName = _appDb.exerciseDao.getNameById(id))
                }

                _state.update { it.copy(selectedExercisesWithNames = selectedExercisesWithNames) }
            }
            catch (e: Exception) {
                _state.update { it.copy(signUpState = SignUpState.Error(
                    "Error while load selected exercises: ${e.message}")) }
            }
        }
    }

    fun updateSelectedExercisesWithNames(selectedExerciseWithName: SelectedExerciseWithName) {
        _state.update {
            val newList = it.selectedExercisesWithNames.map { exercise ->
                if (exercise.selectedExercise.exerciseOwnerId == selectedExerciseWithName.selectedExercise.exerciseOwnerId) {
                    selectedExerciseWithName
                }
                else {
                    exercise
                }
            }
            it.copy(selectedExercisesWithNames = newList)
        }
    }

    private fun assignUserIdToExercises(userId: Int) {
        _state.update {
            val updatedList = it.selectedExercisesWithNames.map { exerciseWithName ->
                exerciseWithName.selectedExercise.userOwnerId = userId
                exerciseWithName
            }
            it.copy(selectedExercisesWithNames = updatedList)
        }
    }
    //endregion
}


data class SignUpScreenState(
    val username: String = "",
    val age: String = "",
    val weight: String = "",
    val gender: Gender = Gender.Male,
    val workoutId: Int? = null,
    val signUpState: SignUpState = SignUpState.Initial,
    val workoutPlans: List<WorkoutPlan> = listOf(),
    val muscleGroupWithExercisesMap: MutableMap<String, List<Exercise>> = mutableMapOf(),
    val selectedExercisesWithNames: List<SelectedExerciseWithName> = listOf(),
    val exercises: List<Exercise> = listOf(),
    val toggledExerciseIds: Set<Int> = setOf(),
    val fieldsErrorState: ErrorsState = ErrorsState()
)

data class ErrorsState(
    var username: String = "",
    var age: String = "",
    var weight: String = "",
) {
    fun isValid(): Boolean {
        return username.isEmpty() && age.isEmpty() && weight.isEmpty()
    }
}

sealed class SignUpState {
    object Initial : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}

object Constants {
    val MIN_AGE = 10
    val MAX_AGE = 120
    val MIN_WEIGHT = 20f
    val MAX_WEIGHT = 400f
    val REQUIRED_FIELD_MESSAGE = "Обязательное поле"
    val WRONG_AGE_MESSAGE = "Значение должно быть больше $MIN_AGE и меньше $MAX_AGE"
    val WRONG_WEIGHT_MESSAGE = "Значение должно быть больше $MIN_WEIGHT и меньше $MAX_WEIGHT"
    val INVALID_FIELD_VALUE_MESSAGE = "Некорректное значение"
    val INVALID_FIELDS_DATA_MESSAGE = "Некорректные данные для создания аккаунта"
    val genderVariants = listOf(Gender.Male, Gender.Female)
}