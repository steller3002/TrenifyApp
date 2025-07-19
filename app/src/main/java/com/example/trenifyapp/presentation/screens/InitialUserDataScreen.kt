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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trenifyapp.R
import com.example.trenifyapp.presentation.components.ConditionButton
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.components.ValidatedTextField

import com.example.trenifyapp.presentation.viewmodels.RegistrationViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialUserDataScreen(
    nextScreen: () -> Unit,
    viewModel: RegistrationViewModel
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
            text = stringResource(R.string.initial_user_data_screen_title),
            modifier = Modifier.padding(bottom = 15.dp)
        )

        ValidatedTextField(
            value = state.userData.username,
            onValueChanged = { viewModel.changeUsername(it) },
            label = stringResource(R.string.username_field),
            errorValue = state.fieldErrors.usernameError
        )

        ValidatedTextField(
            value = state.userData.age,
            onValueChanged = { viewModel.changeAge(it) },
            label = stringResource(R.string.age_field),
            errorValue = state.fieldErrors.ageError,
            keyboardType = KeyboardType.Number
        )

        ValidatedTextField(
            value = state.userData.weight,
            onValueChanged = { viewModel.changeWeight(it) },
            label = stringResource(R.string.weight_field),
            errorValue = state.fieldErrors.weightError,
            keyboardType = KeyboardType.Number
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            state.genders.forEach { gender ->
                GenderButton(
                    onClick = { viewModel.changeGender(gender) },
                    text = gender.displayName,
                    containerColor = if (state.userData.gender == gender) Orange else Color.White
                )
            }
        }

        ConditionButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                nextScreen() },
            text = stringResource(R.string.continue_button),
            enabledCondition = state.userDataIsValid
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