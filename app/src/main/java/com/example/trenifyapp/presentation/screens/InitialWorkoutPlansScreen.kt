package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trenifyapp.presentation.components.DefaultButton
import com.example.trenifyapp.presentation.components.ErrorMessage
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.components.WorkoutPlanItem
import com.example.trenifyapp.presentation.viewmodels.RegistrationViewModel

@Composable
fun InitialWorkoutPlansScreen(
    navigateToWorkoutStatsScreen: () -> Unit,
    viewModel: RegistrationViewModel
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TrenifyTitle()
            ScreenTitle(
                text = "Выберите план тренировок",
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.dataForRegistration == null) {
                ErrorMessage()
            }
            else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
                ) {
                    items(state.dataForRegistration!!.workoutPlans) { plan ->
                        WorkoutPlanItem(
                            plan = plan,
                            isSelected = state.userData.workoutPlanId == plan.workoutPlanId,
                            onSelect = { viewModel.changeWorkoutPlan(plan.workoutPlanId!!) },
                        )
                    }
                }
            }
        }


        DefaultButton(
            onClick = navigateToWorkoutStatsScreen,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            text = "Продолжить"
        )
    }
}
