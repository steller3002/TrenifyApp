package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.presentation.viewmodels.SignUpState
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialUserDataScreen(
    navigateToWorkoutPlansScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val focusManager = LocalFocusManager.current

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
        Text(
            text = "Trenify",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Orange,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Создание аккаунта",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = viewModel.username.value,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text("Имя пользователя") },
            isError = viewModel.fieldsErrorState.value.usernameError != "",
            supportingText = {
                if (viewModel.fieldsErrorState.value.usernameError != "") {
                    Text(viewModel.fieldsErrorState.value.usernameError)
                }
            }
        )

        OutlinedTextField(
            value = viewModel.age.value,
            onValueChange = { viewModel.updateAge(it) },
            label = { Text("Возраст") },
            isError = viewModel.fieldsErrorState.value.ageError != "",
            supportingText = {
                if (viewModel.fieldsErrorState.value.ageError != "") {
                    Text(viewModel.fieldsErrorState.value.ageError)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = viewModel.weight.value,
            onValueChange = { viewModel.updateWeight(it) },
            label = { Text("Вес") },
            isError = viewModel.fieldsErrorState.value.weightError != "",
            supportingText = {
                if (viewModel.fieldsErrorState.value.weightError != "") {
                    Text(viewModel.fieldsErrorState.value.weightError)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row {
            viewModel.genderVariants.forEach { gender ->
                Button(
                    onClick = { viewModel.changeGender(gender) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewModel.gender.value == gender) Orange else Color.LightGray
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(gender.displayName)
                }
            }
        }

        Button(
            onClick = {
                if (viewModel.fieldsIsValid()) {
                    navigateToWorkoutPlansScreen()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White
            )
        ) {
            Text("Продолжить")
        }
    }
}