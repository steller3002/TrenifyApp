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
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.presentation.components.ConditionButton
import com.example.trenifyapp.presentation.components.ScreenTitle
import com.example.trenifyapp.presentation.components.TrenifyTitle
import com.example.trenifyapp.presentation.components.WorkoutPlanItem
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun InitialWorkoutPlansScreen(
    navigateToWorkoutStatsScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val state by viewModel.state.collectAsState()
    val workoutPlans = state.workoutPlans

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
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
            ) {
                items(workoutPlans) { plan ->
                    WorkoutPlanItem(
                        plan = plan,
                        isSelected = state.workoutId == plan.workoutPlanId,
                        onSelect = { viewModel.changeWorkoutId(plan.workoutPlanId!!) },
                    )
                }
            }
        }


        ConditionButton(
            onClick = navigateToWorkoutStatsScreen,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            enabledCondition = state.workoutId != null,
            text = "Продолжить"
        )
    }
}
