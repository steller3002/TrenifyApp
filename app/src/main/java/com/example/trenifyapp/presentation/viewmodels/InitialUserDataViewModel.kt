package com.example.trenifyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.domain.enums.Gender
import com.example.trenifyapp.domain.usecases.ValidateAgeUseCase
import com.example.trenifyapp.domain.usecases.ValidateUsernameUseCase
import com.example.trenifyapp.domain.usecases.ValidateWeightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialUserDataViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateAgeUseCase: ValidateAgeUseCase,
    private val validateWeightUseCase: ValidateWeightUseCase
) : ViewModel() {
    private var _state = MutableStateFlow(InitialUserDataScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<InitialUserDataScreenEvent>()
    val event = _event.receiveAsFlow()

    fun onEvent(event: InitialUserDataScreenEvent) {
        when(event) {
            is InitialUserDataScreenEvent.UsernameChanged -> {
                _state.update { it.copy(username = event.username) }
            }
            is InitialUserDataScreenEvent.AgeChanged -> {
                _state.update { it.copy(age = event.age) }
            }
            is InitialUserDataScreenEvent.WeightChanged -> {
                _state.update { it.copy(weight = event.weight) }
            }
            is InitialUserDataScreenEvent.GenderChanged -> {
                _state.update { it.copy(gender = event.gender) }
            }
            is InitialUserDataScreenEvent.Submit -> {
                validateFields()
            }
            is InitialUserDataScreenEvent.SuccessValidated -> {
                clearErrors()
            }
            is InitialUserDataScreenEvent.Idle -> { }
        }
    }

    private fun validateFields() {
        viewModelScope.launch {
            val usernameResult = validateUsernameUseCase.invoke(_state.value.username)
            val ageResult = validateAgeUseCase.invoke(_state.value.age)
            val weightResult = validateWeightUseCase.invoke(_state.value.weight)

            val hasError = listOf(usernameResult, ageResult, weightResult)
                .any { it.isFailure }

            if (hasError) {
                _state.update { it.copy(
                    usernameError = usernameResult.exceptionOrNull()?.message,
                    ageError = ageResult.exceptionOrNull()?.message,
                    weightError = weightResult.exceptionOrNull()?.message
                ) }
                return@launch
            }

            _event.send(InitialUserDataScreenEvent.SuccessValidated)
        }
    }

    private fun clearErrors() {
        _state.update { it.copy(usernameError = null, ageError = null, weightError = null) }
    }
}

data class InitialUserDataScreenState(
    val username: String = "",
    val age: String = "",
    val weight: String = "",
    val gender: Gender = Gender.Male,

    val usernameError: String? = null,
    val ageError: String? = null,
    val weightError: String? = null,
)

sealed class InitialUserDataScreenEvent {
    data object Idle: InitialUserDataScreenEvent()
    data class UsernameChanged(val username: String): InitialUserDataScreenEvent()
    data class AgeChanged(val age: String): InitialUserDataScreenEvent()
    data class WeightChanged(val weight: String): InitialUserDataScreenEvent()
    data class GenderChanged(val gender: Gender): InitialUserDataScreenEvent()
    data object SuccessValidated: InitialUserDataScreenEvent()
    data object Submit: InitialUserDataScreenEvent()
}