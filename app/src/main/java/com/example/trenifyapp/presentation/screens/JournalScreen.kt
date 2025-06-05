package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trenifyapp.R
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.presentation.dataclasses.WorkoutDateWithPhaseName
import com.example.trenifyapp.presentation.viewmodels.JournalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    userId: Int,
    viewModel: JournalViewModel = viewModel(),
    onWorkoutClick: (workoutId: Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getWorkoutDatesWithPhaseNames(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Журнал тренировок",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WorkoutList(
                workoutDatesWithPhaseNames = state.workoutDatesWithPhaseNames,
                onWorkoutClick = onWorkoutClick
            )
        }
    }
}

@Composable
private fun WorkoutList(
    workoutDatesWithPhaseNames: List<WorkoutDateWithPhaseName>,
    onWorkoutClick: (workoutId: Int) -> Unit
) {
    when {
        workoutDatesWithPhaseNames.isEmpty() -> {
            Text(
                text = "Загружаем тренировки",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workoutDatesWithPhaseNames) { workout ->
                    WorkoutItem(
                        workoutDateWithPhaseName = workout,
                        onClick = { onWorkoutClick(workout.workoutId.toInt()) }
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkoutItem(
    workoutDateWithPhaseName: WorkoutDateWithPhaseName,
    onClick: () -> Unit
) {
    val dateFormatter = remember {
        SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = dateFormatter.format(workoutDateWithPhaseName.date),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = workoutDateWithPhaseName.phaseName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}