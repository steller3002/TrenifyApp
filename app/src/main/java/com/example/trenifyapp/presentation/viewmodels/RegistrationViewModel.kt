package com.example.trenifyapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.domain.dataclasses.DataForRegistration
import com.example.trenifyapp.domain.dataclasses.ToggleExerciseParams
import com.example.trenifyapp.domain.enums.Gender
import com.example.trenifyapp.domain.usecases.CreateAccountUseCase
import com.example.trenifyapp.domain.usecases.LoadDataForRegistrationUseCase
import com.example.trenifyapp.domain.usecases.ToggleExerciseUseCase
import com.example.trenifyapp.domain.usecases.ValidateAgeUseCase
import com.example.trenifyapp.domain.usecases.ValidateUsernameUseCase
import com.example.trenifyapp.domain.usecases.ValidateWeightUseCase
import com.example.trenifyapp.presentation.dataclasses.ToggledExerciseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateAgeUseCase: ValidateAgeUseCase,
    private val validateWeightUseCase: ValidateWeightUseCase,
    private val loadDataForRegistrationUseCase: LoadDataForRegistrationUseCase,
    private val createAccountUseCase: CreateAccountUseCase,
    private val toggleExerciseUseCase: ToggleExerciseUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    init {
        loadDataForRegistration()
    }

    fun changeUsername(text: String) {
        validateTextAndUpdate(
            text = text,
            validateFunc = validateUsernameUseCase::invoke,
            updateErrorFunc = { copy(fieldErrors = fieldErrors.copy(usernameError = it)) },
            updateFieldFunc = { copy(userData = userData.copy(username = text)) },
        )
    }

    fun changeAge(text: String) {
        validateTextAndUpdate(
            text = text,
            validateFunc = validateAgeUseCase::invoke,
            updateErrorFunc = { copy(fieldErrors = fieldErrors.copy(ageError = it)) },
            updateFieldFunc = { copy(userData = userData.copy(age = text)) },
        )
    }

    fun changeWeight(text: String) {
        validateTextAndUpdate(
            text = text,
            validateFunc = validateWeightUseCase::invoke,
            updateErrorFunc = { copy(fieldErrors = fieldErrors.copy(weightError = it)) },
            updateFieldFunc = { copy(userData = userData.copy(weight = text)) },
        )
    }

    fun changeGender(gender: Gender) = _state.update { it.copy(userData = it.userData.copy(gender = gender)) }
    fun changeWorkoutPlan(id: Int) = _state.update { it.copy(userData = it.userData.copy(workoutPlanId = id)) }

    fun toggleExercise(toggledExerciseInfo: ToggledExerciseInfo) {
        if (_state.value.dataForRegistration == null) return

        val params = ToggleExerciseParams(
            toggledExerciseInfo = toggledExerciseInfo,
            toggledExercisesPerMuscleGroup = _state.value.dataForRegistration!!.toggledExercisesPerMuscleGroup,
            toggledExercises = _state.value.userData.toggledExercises
        )

        val result = toggleExerciseUseCase.invoke(params)
        if (result.isFailure) {
            Log.e(RegistrationViewModel::class.simpleName, "Ошибка выбора упражнения", result.exceptionOrNull())
            return
        }

        val toggleResult = result.getOrThrow()
        _state.update { it.copy(
            userData = it.userData.copy(toggledExercises = toggleResult.toggledExercises),
            dataForRegistration = it.dataForRegistration?.copy(toggledExercisesPerMuscleGroup = toggleResult.toggledExercisesPerMuscleGroup)
        ) }

         updateExerciseNumberValidity()
    }

    fun createAccount() {
        viewModelScope.launch {
            val userData = _state.value.userData
            val ageAsInt = userData.age.toIntOrNull()
            val weightAsFloat = userData.weight.toFloatOrNull()

            if (ageAsInt == null || weightAsFloat == null) {
                Log.e(RegistrationViewModel::class.simpleName, "Некорректный возраст/вес")
                return@launch
            }

            try {
                createAccountUseCase.invoke(
                    username = userData.username,
                    age = ageAsInt,
                    weight = weightAsFloat,
                    gender = userData.gender,
                    workoutId = userData.workoutPlanId,
                    toggledExercises = userData.toggledExercises
                )
            }
            catch (e: Exception) {
                Log.e(RegistrationViewModel::class.simpleName, "Ошибка создания пользователя", e)
            }
        }
    }

    private fun updateExerciseNumberValidity() {
        val minExercisesOnGroup = _state.value.minExercisesOnGroup
        val isValid = _state.value.dataForRegistration?.toggledExercisesPerMuscleGroup?.all { it >=  minExercisesOnGroup }
        if (isValid != null) {
            _state.update { it.copy(exercisesNumberIsValid = isValid) }
        }
    }

    private fun loadDataForRegistration() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val dataResult = loadDataForRegistrationUseCase.invoke()

            dataResult.fold(
                onSuccess = { data ->
                    _state.update { it.copy(dataForRegistration = data, isLoading = false) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, loadingError = error.message) }
                    Log.e(RegistrationViewModel::class.simpleName, "Не удалось загрузить данные", error)
                }
            )
        }
    }

    private fun validateTextAndUpdate(
        text: String,
        validateFunc: (String) -> Result<Unit>,
        updateFieldFunc: RegistrationState.(String) -> RegistrationState,
        updateErrorFunc: RegistrationState.(String?) -> RegistrationState
    ) {
        val result = validateFunc(text)
        _state.update { state ->
            val newState = result.fold(
                onSuccess = { state.updateErrorFunc(null) },
                onFailure = {error -> state.updateErrorFunc(error.message) }
            )
            newState.updateFieldFunc(text)
        }

        updateUserDataValidity()
    }

    private fun updateUserDataValidity() {
        val errors = _state.value.fieldErrors
        val userData = _state.value.userData

        val isValid = errors.ageError == null &&
                errors.usernameError == null &&
                errors.weightError == null &&
                userData.username.isNotBlank() &&
                userData.age.isNotBlank() &&
                userData.weight.isNotBlank()

        _state.update { it.copy(userDataIsValid = isValid) }
    }
}

data class RegistrationState(
    val genders: List<Gender> = listOf(Gender.Male, Gender.Female),
    val minExercisesOnGroup: Int = 2,

    val isLoading: Boolean = false,
    val loadingError: String? = null,

    val userData: UserData = UserData(),
    val fieldErrors: FieldErrors = FieldErrors(),

    val dataForRegistration: DataForRegistration? = null,

    val userDataIsValid: Boolean = false,
    val exercisesNumberIsValid: Boolean = false,
)

data class UserData(
    val username: String = "",
    val age: String = "",
    val weight: String = "",
    val gender: Gender = Gender.Male,
    val workoutPlanId: Int = 1,
    val toggledExercises: List<ToggledExerciseInfo> = listOf(),
)

data class FieldErrors(
    val usernameError: String? = null,
    val ageError: String? = null,
    val weightError: String? = null,
)