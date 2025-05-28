package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trenifyapp.presentation.navigation.BottomNavigation
import com.example.trenifyapp.presentation.navigation.ScreenRoute
import com.example.trenifyapp.presentation.navigation.sharedViewModel
import com.example.trenifyapp.presentation.viewmodels.WorkoutViewModel

@Composable
fun UserMainScreen(
    userId: Int,
) {
    val navController = rememberNavController()
    val workoutViewModel: WorkoutViewModel = hiltViewModel()

    LaunchedEffect(userId) {
        workoutViewModel.userId = userId
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, userId = userId)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.JournalScreen.route
            ) {
                composable(ScreenRoute.JournalScreen.route) { backStackEntry ->
                    JournalScreen(userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0)
                }

                composable(ScreenRoute.WorkoutGenerateScreen.route) { backStackEntry ->
                    val viewModel = backStackEntry.sharedViewModel<WorkoutViewModel>(navController)

                    WorkoutGenerateScreen(
                        userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0,
                        viewModel = workoutViewModel,
                        navigateToWorkoutExercisesScreen = {
                            navController.navigate(ScreenRoute.WorkoutExercisesScreen.route)
                        }
                    )
                }

                composable(ScreenRoute.AccountScreen.route) { backStackEntry ->
                    AccountScreen(userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0)
                }

                composable(ScreenRoute.WorkoutExercisesScreen.route) { backStackEntry ->
                    WorkoutExercisesScreen(
                        viewModel = workoutViewModel
                    )
                }
            }
        }
    }
}
