package com.example.trenifyapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.data.entities.ExerciseWithMuscles
import com.example.trenifyapp.data.entities.MuscleGroup
import com.example.trenifyapp.data.entities.User
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.domain.enums.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val _appDb: AppDb
) : ViewModel() {
    private val MIN_AGE = 10
    private val MAX_AGE = 120
    private val MIN_WEIGHT = 20f
    private val MAX_WEIGHT = 400f
    private val REQUIRED_FIELD_MESSAGE = "Обязательное поле"
    private val WRONG_AGE_MESSAGE = "Значение должно быть больше $MIN_AGE и меньше $MAX_AGE"
    private val WRONG_WEIGHT_MESSAGE = "Значение должно быть больше $MIN_WEIGHT и меньше $MAX_WEIGHT"
    private val INVALID_FIELD_VALUE_MESSAGE = "Некорректное значение"
    private val INVALID_FIELDS_DATA_MESSAGE = "Некорректные данные для создания аккаунта"
    private val INVALID_WORKOUT_ID_MESSAGE = "Выбран некорректный план тренировок"

    val genderVariants = listOf(Gender.Male, Gender.Female)

    init {
        loadWorkoutPlans()
        loadExercises()
    }

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Initial)
    val signUpState = _signUpState.asStateFlow()

    private val _workoutPlans = MutableStateFlow<List<WorkoutPlan>>(emptyList())
    val workoutPlans = _workoutPlans.asStateFlow()

    private val _exercises = MutableStateFlow<MutableMap<MuscleGroup, Exercise>>(mutableMapOf())
    val exercises = _exercises.asStateFlow()

    val fieldsErrorState = mutableStateOf(FieldErrorStates())

    // Forms states
    val username = mutableStateOf("")
    val age = mutableStateOf("")
    val weight = mutableStateOf("")
    val gender = mutableStateOf(Gender.Male)
    val workoutId = mutableStateOf<Int?>(null)
    val selectedExerciseIds = mutableStateOf<Set<Int>>(emptySet())

    // Update functions
    fun updateUsername(text: String) { username.value = text.trim() }
    fun updateAge(text: String) { age.value = text.trim() }
    fun updateWeight(text: String) { weight.value = text.trim() }
    fun changeGender(gender: Gender) { this.gender.value = gender }
    fun changeWorkoutId(workoutId: Int) { this.workoutId.value = workoutId }

    fun toggleExerciseSelection(exerciseId: Int) {
        selectedExerciseIds.value = selectedExerciseIds.value.toMutableSet().also { set ->
            if (set.contains(exerciseId)) set.remove(exerciseId)
            else set.add(exerciseId)
        }
    }

    //Load Functions
    fun loadWorkoutPlans() {
        viewModelScope.launch {
            _appDb.workoutPlanDao.getAll().collect() { plans ->
                _workoutPlans.value = plans
            }
        }
    }

    fun loadExercises() {
        viewModelScope.launch {
            var exercisesWithMuscles = listOf<ExerciseWithMuscles>()
            _appDb.exerciseDao.getAllWithMuscles().collect { list ->
                exercisesWithMuscles = list
            }


        }
    }

    // Validate functions
    private fun validateUsername() {
        fieldsErrorState.value = if (username.value.isBlank()) {
            fieldsErrorState.value.copy(usernameError = REQUIRED_FIELD_MESSAGE)
        }
        else {
            fieldsErrorState.value.copy(usernameError = "")
        }
    }

    private fun validateAge() {
        fieldsErrorState.value = when {
            age.value.isBlank() -> fieldsErrorState.value.copy(ageError = REQUIRED_FIELD_MESSAGE)
            else -> {
                try {
                    if (age.value.toInt() !in MIN_AGE..MAX_AGE)
                        fieldsErrorState.value.copy(ageError = WRONG_AGE_MESSAGE)
                    else fieldsErrorState.value.copy(ageError = "")
                }
                catch (_: Exception) {
                    fieldsErrorState.value.copy(ageError = INVALID_FIELD_VALUE_MESSAGE)
                }
            }
        }
    }

    private fun validateWeight() {
        fieldsErrorState.value = when {
            weight.value.isBlank() -> fieldsErrorState.value.copy(weightError = REQUIRED_FIELD_MESSAGE)
            else -> {
                try {
                    if (weight.value.toFloat() !in MIN_WEIGHT..MAX_WEIGHT) {
                        fieldsErrorState.value.copy(weightError = WRONG_WEIGHT_MESSAGE)
                    }
                    else fieldsErrorState.value.copy(weightError = "")
                }
                catch (_: Exception) {
                    fieldsErrorState.value.copy(weightError = INVALID_FIELD_VALUE_MESSAGE)
                }
            }
        }
    }

    private fun workoutIdExists() : Boolean {
        return _workoutPlans.value.any{ it.id == workoutId.value }
    }

    fun fieldsIsValid() : Boolean {
        validateUsername()
        validateAge()
        validateWeight()

        if (fieldsErrorState.value.isValid()) {
            return true
        }

        _signUpState.value = SignUpState.Error(INVALID_FIELDS_DATA_MESSAGE)
        return false
    }

    fun createAccount() {
        viewModelScope.launch {
            try {
                if (!fieldsIsValid()) {
                    _signUpState.value = SignUpState.Error(INVALID_FIELDS_DATA_MESSAGE)
                    return@launch
                }
                if (!workoutIdExists()) {
                    _signUpState.value = SignUpState.Error(INVALID_WORKOUT_ID_MESSAGE)
                }

                val user = User(
                    username = username.value,
                    age = age.value.toInt(),
                    gender = gender.value,
                    weight = weight.value.toFloat(),
                    workoutProgramId = workoutId.value!!
                )

                _appDb.userDao.create(user)
                _signUpState.value = SignUpState.Success
            }
            catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message.toString())
            }
        }
    }
}

sealed class SignUpState {
    object Initial: SignUpState()
    object FirstStepSuccess : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}

data class FieldErrorStates(
    var usernameError: String = "",
    var ageError: String = "",
    var weightError: String = "",
) {
    fun isValid() : Boolean {
        return usernameError.isEmpty() && ageError.isEmpty() && weightError.isEmpty()
    }
}