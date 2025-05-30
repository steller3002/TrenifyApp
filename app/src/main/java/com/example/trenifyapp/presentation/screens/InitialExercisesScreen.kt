package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.trenifyapp.data.entities.Exercise
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialExercisesScreen(
    viewModel: SignUpViewModel,
    navigateToSettingUpExercisesScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val selectedExerciseIds = state.toggledExerciseIds
    val muscleGroupsWithExercises = state.muscleGroupWithExercisesMap

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
            ) {
                muscleGroupsWithExercises.keys.forEach { muscleGroupName ->
                    val exercises = muscleGroupsWithExercises[muscleGroupName] ?: emptyList()

                    item {
                        Text(
                            text = muscleGroupName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }

                    items(exercises.sortedBy { it.name }) { exercise ->
                        ExerciseItem(
                            exercise = exercise,
                            isSelected = selectedExerciseIds.contains(exercise.exerciseId),
                            onToggleSelection = {
                                exercise.exerciseId?.let { id ->
                                    viewModel.toggleExerciseSelection(id)
                                }
                            }
                        )
                    }
                }
            }
        }


        Button(
            onClick = {
                navigateToSettingUpExercisesScreen()
                viewModel.createSelectedExercisesWithNames()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .fillMaxWidth()
                .height(56.dp),
            enabled = selectedExerciseIds.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text("Продолжить")
        }
    }
}

@Composable
private fun ExerciseItem(
    exercise: Exercise,
    isSelected: Boolean,
    onToggleSelection: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleSelection() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Orange.copy(alpha = 0.2f)
            else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleSelection() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Orange,
                    uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (exercise.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = exercise.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
