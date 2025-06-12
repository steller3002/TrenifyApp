package com.example.trenifyapp.presentation.screens

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
import androidx.compose.ui.unit.dp
import com.example.trenifyapp.presentation.components.DefaultButton
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.components.ValidatedTextField
import com.example.trenifyapp.presentation.viewmodels.Constants
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialUserDataScreen(
    navigateToWorkoutPlansScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsState()

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
            onValueChanged = { viewModel.updateUsername(it) },
            label = "Имя пользователя",
            errorValue = state.fieldsErrorState.username
        )

        ValidatedTextField(
            value = state.age,
            onValueChanged = { viewModel.updateAge(it) },
            label = "Возраст",
            errorValue = state.fieldsErrorState.age,
            keyboardType = KeyboardType.Number
        )

        ValidatedTextField(
            value = state.weight,
            onValueChanged = { viewModel.updateWeight(it) },
            label = "Вес",
            errorValue = state.fieldsErrorState.weight,
            keyboardType = KeyboardType.Number
        )

        Row {
            Constants.GENDER_VARIANTS.forEach { gender ->
                Button(
                    onClick = { viewModel.changeGender(gender) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.gender == gender) Orange else Color.LightGray
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(gender.displayName)
                }
            }
        }

        DefaultButton(
            onClick = {
                if (viewModel.fieldsIsValid()) {
                    navigateToWorkoutPlansScreen()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            text = "Продолжить"
        )
    }
}