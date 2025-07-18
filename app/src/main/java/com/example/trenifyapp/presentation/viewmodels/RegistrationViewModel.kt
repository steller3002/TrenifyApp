package com.example.trenifyapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.domain.dataclasses.DataForRegistration
import com.example.trenifyapp.domain.enums.Gender
import com.example.trenifyapp.domain.usecases.LoadDataForRegistrationUseCase
import com.example.trenifyapp.domain.usecases.ValidateAgeUseCase
import com.example.trenifyapp.domain.usecases.ValidateExerciseWeightUseCase
import com.example.trenifyapp.domain.usecases.ValidateSetsUseCase
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
    private val validateSetsUseCase: ValidateSetsUseCase,
    private val validateExerciseWeightUseCase: ValidateExerciseWeightUseCase,
    private val loadDataForRegistrationUseCase: LoadDataForRegistrationUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    init {
        loadDataForRegistration()
    }

    fun validateAndChangeUsername(text: String) {
        validateTextAndUpdate(
            text = text,
            validateFunc = validateUsernameUseCase::invoke,
            updatedError = { copy(fieldErrors = fieldErrors.copy(usernameError = it)) },
            updatedField = { copy(userData = userData.copy(username = text)) },
        )
        updateUserDataValidity()
    }

    fun changeAge(text: String) {
        validateTextAndUpdate(
            text = text,
            validateFunc = validateAgeUseCase::invoke,
            updatedError = { copy(fieldErrors = fieldErrors.copy(ageError = it)) },
            updatedField = { copy(userData = userData.copy(age = text)) },
        )
        updateUserDataValidity()
    }

    fun changeWeight(text: String) {
        validateTextAndUpdate(
            text = text,
            validateFunc = validateWeightUseCase::invoke,
            updatedError = { copy(fieldErrors = fieldErrors.copy(weightError = it)) },
            updatedField = { copy(userData = userData.copy(weight = text)) },
        )
        updateUserDataValidity()
    }

    fun changeGender(gender: Gender) = _state.update { it.copy(userData = it.userData.copy(gender = gender)) }
    fun changeWorkoutPlan(id: Int) = _state.update { it.copy(userData = it.userData.copy(workoutPlanId = id)) }

    fun toggleExercise(toggledExerciseInfo: ToggledExerciseInfo) {
        if (_state.value.userData.toggledExercises.contains(toggledExerciseInfo)) {
            _state.update { it.copy(userData = it.userData.copy(
                toggledExercises = it.userData.toggledExercises.minus(toggledExerciseInfo))
            ) }
        }
        else {
            _state.update { it.copy(userData = it.userData.copy(
                toggledExercises = it.userData.toggledExercises.plus(toggledExerciseInfo))
            ) }
        }
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
        updatedField: RegistrationState.(String) -> RegistrationState,
        updatedError: RegistrationState.(String?) -> RegistrationState
    ) {
        val result = validateFunc(text)
        _state.update { state ->
            val newState = result.fold(
                onSuccess = { state.updatedError(null) },
                onFailure = {error -> state.updatedError(error.message) }
            )
            newState.updatedField(text)
        }
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
)

data class UserData(
    val username: String = "",
    val age: String = "",
    val weight: String = "",
    val gender: Gender = Gender.Male,
    val workoutPlanId: Int = 1,
    val toggledExercises: List<ToggledExerciseInfo> = listOf()
)

data class FieldErrors(
    val usernameError: String? = null,
    val ageError: String? = null,
    val weightError: String? = null,
    val setsError: String? = null,
    val repsError: String? = null,
)