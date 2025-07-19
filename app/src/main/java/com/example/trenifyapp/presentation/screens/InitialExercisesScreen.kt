package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.presentation.components.ConditionButton
import com.example.trenifyapp.presentation.components.ExerciseItem
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.dataclasses.ToggledExerciseInfo
import com.example.trenifyapp.presentation.viewmodels.RegistrationViewModel

@Composable
fun InitialExercisesScreen(
    viewModel: RegistrationViewModel,
    toAccountsScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
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
                text = "Минимум ${state.minExercisesOnGroup} на каждую группу мышц"
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.dataForRegistration == null) {
                Text("Возникла ошибка :(")
            }
            else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
                ) {
                    state.dataForRegistration!!.muscleGroupNamesWithExercises.forEach { kvPair ->
                        val muscleGroupName = kvPair.key

                        val exercises = state.dataForRegistration!!.muscleGroupNamesWithExercises[muscleGroupName]
                            ?: emptyList()

                        item {
                            Text(
                                text = muscleGroupName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                            )
                        }

                        items(exercises.sortedBy { it.name }) { exercise ->
                            val toggledExerciseInfo = ToggledExerciseInfo(
                                id = exercise.exerciseId!!,
                                name = exercise.name,
                                muscleGroupId = exercise.muscleGroupOwnerId
                            )

                            ExerciseItem(
                                exercise = exercise,
                                isSelected = state.userData.toggledExercises.contains(toggledExerciseInfo),
                                onToggleSelection = {
                                    viewModel.toggleExercise(toggledExerciseInfo)
                                }
                            )
                        }
                    }
                }
            }
        }

        ConditionButton(
            onClick = {
                viewModel.createAccount()
                toAccountsScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter),
            enabledCondition = state.exercisesNumberIsValid,
            text = "Создать аккаунт"
        )
    }
}
