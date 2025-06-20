package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.presentation.components.ConditionButton
import com.example.trenifyapp.presentation.components.ExerciseItem
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.viewmodels.Constants
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel

@Composable
fun InitialExercisesScreen(
    viewModel: SignUpViewModel,
    navigateToSettingUpExercisesScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

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
            TrenifyTitle()
            ScreenTitle(
                text = "Выберите упражнения",
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 15.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                text = "Минимум ${Constants.MIN_EXERCISES_NUMBER} на каждую группу мышц"
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
            ) {
                state.muscleGroupNamesWithExercises.keys.forEach { muscleGroupName ->
                    val exercises = state.muscleGroupNamesWithExercises[muscleGroupName] ?: emptyList()

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
                            isSelected = state.toggledExerciseIds.contains(exercise.exerciseId),
                            onToggleSelection = {
                                viewModel.toggleExerciseSelection(exercise.exerciseId!!, muscleGroupName)
                                viewModel.checkNumberOfExercises()
                            }
                        )
                    }
                }
            }
        }

        ConditionButton(
            onClick = {
                navigateToSettingUpExercisesScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter),
            enabledCondition = state.fieldsErrorState.numberOfExercises == "" &&
                state.toggledExerciseIds.isNotEmpty(),
            text = "Продолжить"
        )
    }
}
