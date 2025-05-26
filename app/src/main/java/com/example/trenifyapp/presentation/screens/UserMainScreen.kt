package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trenifyapp.presentation.navigation.BottomNavigation
import com.example.trenifyapp.presentation.navigation.ScreenRoute
import com.example.trenifyapp.presentation.viewmodels.WorkoutViewModel

@Composable
fun UserMainScreen(
    userId: Int,
) {
    val navController = rememberNavController()

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

                composable(ScreenRoute.WorkoutScreen.route) { backStackEntry ->
                    val viewModel: WorkoutViewModel = hiltViewModel()

                    WorkoutScreen(
                        userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0,
                        viewModel = viewModel
                    )
                }

                composable(ScreenRoute.AccountScreen.route) { backStackEntry ->
                    AccountScreen(userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0)
                }
            }
        }
    }
}
