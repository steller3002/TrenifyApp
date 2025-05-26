package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.presentation.dataclasses.SelectedExerciseWithName
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialSettingUpExercisesScreen(
    viewModel: SignUpViewModel,
    navigateToAccountsScreen: () -> Unit
) {
    /*TODO:
    Добавить необходимость выбора по меньшей мере двух упражнений для каждой группы мышц
     */

    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val selectedExercisesWithNames = state.selectedExercisesWithNames

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Trenify",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Orange,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Настройка упражнений (${selectedExercisesWithNames.size})",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedExercisesWithNames.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Вы не выбрали ни одного упражнения",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(selectedExercisesWithNames) { exercise ->
                    ConfigurableExerciseItem(
                        exercise = exercise,
                        onUpdate = { updatedExercise ->
                            viewModel.updateSelectedExercisesWithNames(updatedExercise)
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.createAccount()
                navigateToAccountsScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White
            )
        ) {
            Text("Создать аккаунт")
        }
    }
}

@Composable
private fun ConfigurableExerciseItem(
    exercise: SelectedExerciseWithName,
    onUpdate: (SelectedExerciseWithName) -> Unit
) {
    var sets by remember { mutableStateOf(exercise.selectedExercise.setsNumber.toString()) }
    var reps by remember { mutableStateOf(exercise.selectedExercise.repeatsNumber.toString()) }
    var weight by remember { mutableStateOf(exercise.selectedExercise.currentWorkingWeight.toString()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Название упражнения
            Text(
                text = exercise.exerciseName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Поле для ввода подходов
            OutlinedTextField(
                value = sets,
                onValueChange = {
                    sets = it
                    exercise.selectedExercise.setsNumber = it.toIntOrNull() ?: 0
                    onUpdate(exercise)
                },
                label = { Text("Количество подходов") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Поле для ввода повторений
            OutlinedTextField(
                value = reps,
                onValueChange = {
                    reps = it
                    exercise.selectedExercise.repeatsNumber = it.toIntOrNull() ?: 0
                    onUpdate(exercise)
                },
                label = { Text("Количество повторений") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Поле для ввода веса
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    exercise.selectedExercise.currentWorkingWeight = it.toFloatOrNull() ?: 0f
                    onUpdate(exercise)
                },
                label = { Text("Рабочий вес (кг)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}