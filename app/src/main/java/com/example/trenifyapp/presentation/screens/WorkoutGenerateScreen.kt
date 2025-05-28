package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.presentation.viewmodels.WorkoutViewModel
import com.example.trenifyapp.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun WorkoutGenerateScreen(
    userId: Int,
    viewModel: WorkoutViewModel,
    navigateToWorkoutExercisesScreen: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    var triggerNavigation by remember { mutableStateOf(false) }

    LaunchedEffect(triggerNavigation) {
        if (triggerNavigation) {
            delay(3000)
            navigateToWorkoutExercisesScreen()
            triggerNavigation = false
            isLoading = false
        }
    }

    LaunchedEffect(userId) {
        viewModel.userId = userId
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Trenify",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Генерация тренировки...")
                }
            } else {
                Button(
                    onClick = {
                        isLoading = true
                        viewModel.generateWorkoutAndLoadWorkoutExercises()
                        triggerNavigation = true
                    },
                    modifier = Modifier.size(300.dp)
                        .padding(16.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Сгенерировать",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    )
                }
            }
        }
    }
}