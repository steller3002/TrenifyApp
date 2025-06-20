package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trenifyapp.presentation.components.DefaultButton
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.components.ValidatedTextField
import com.example.trenifyapp.presentation.viewmodels.Constants
import com.example.trenifyapp.presentation.viewmodels.InitialUserDataScreenEvent
import com.example.trenifyapp.presentation.viewmodels.InitialUserDataViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialUserDataScreen(
    nextScreen: () -> Unit,
    viewModel: InitialUserDataViewModel
) {
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsState(initial = InitialUserDataScreenEvent.Idle)

    LaunchedEffect(event) {
        when(event) {
            is InitialUserDataScreenEvent.SuccessValidated -> {
                viewModel.onEvent(InitialUserDataScreenEvent.SuccessValidated)
                nextScreen()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TrenifyTitle()
        ScreenTitle(
            text = "Создание аккаунта",
            modifier = Modifier.padding(bottom = 15.dp)
        )

        ValidatedTextField(
            value = state.username,
            onValueChanged = { viewModel.onEvent(InitialUserDataScreenEvent.UsernameChanged(it)) },
            label = "Имя пользователя",
            errorValue = state.usernameError
        )

        ValidatedTextField(
            value = state.age,
            onValueChanged = { viewModel.onEvent(InitialUserDataScreenEvent.AgeChanged(it)) },
            label = "Возраст",
            errorValue = state.ageError,
            keyboardType = KeyboardType.Number
        )

        ValidatedTextField(
            value = state.weight,
            onValueChanged = { viewModel.onEvent(InitialUserDataScreenEvent.WeightChanged(it)) },
            label = "Вес",
            errorValue = state.weightError,
            keyboardType = KeyboardType.Number
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Constants.GENDER_VARIANTS.forEach { gender ->
                GenderButton(
                    onClick = { viewModel.onEvent(InitialUserDataScreenEvent.GenderChanged(gender)) },
                    text = gender.displayName,
                    containerColor = if (state.gender == gender) Orange else Color.White
                )
            }
        }

        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { viewModel.onEvent(InitialUserDataScreenEvent.Submit) },
            text = "Продолжить"
        )
    }
}

@Composable
fun GenderButton(
    onClick: () -> Unit,
    borderColor: Color = Orange,
    textColor: Color = Color.Black,
    containerColor: Color = Orange,
    text: String,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(2.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            disabledContainerColor = Color.Transparent,
            containerColor = containerColor),
    ) {
        Text(text)
    }
}

@Composable
@Preview
private fun GenderButtonPreview() {
    GenderButton(
        borderColor = Orange,
        onClick = {},
        text = "Пол"
    )
}