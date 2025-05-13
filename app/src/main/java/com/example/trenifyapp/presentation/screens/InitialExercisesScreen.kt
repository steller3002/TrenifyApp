package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialExercisesScreen(
    viewModel: SignUpViewModel,
    navigateToAccountsScreen: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val exercises by viewModel.exercises.collectAsState()
    val selectedExercises = remember { mutableStateListOf<Int>() }

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
            text = "Выберите упражнения",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(exercises.size) { index ->
                val exercise = exercises[index]
                val isSelected = selectedExercises.contains(exercise.exerciseId)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            if (isSelected) {
                                selectedExercises.remove(exercise.exerciseId)
                            } else {
                                selectedExercises.add(exercise.exerciseId!!)
                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Orange else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = exercise.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                            if (exercise.description.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = exercise.description,
                                    fontSize = 14.sp,
                                    color = if (isSelected) Color.White.copy(0.8f)
                                    else MaterialTheme.colorScheme.onSurface.copy(0.8f)
                                )
                            }
                        }

                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = {
                                if (isSelected) {
                                    selectedExercises.remove(exercise.exerciseId)
                                } else {
                                    selectedExercises.add(exercise.exerciseId!!)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                // сюда можно передать selectedExercises в ViewModel или в навигацию
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
            Text("Продолжить")
        }
    }
}
