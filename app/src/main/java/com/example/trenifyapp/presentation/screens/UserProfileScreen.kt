package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trenifyapp.presentation.navigation.BottomNavigation

@Composable
fun UserProfileScreen(
    userId: Int,
    viewModel: ViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = "Journal"
            ) {
                composable("Journal") { JournalScreen() }
                composable("Workout") { WorkoutScreen() }
                composable("Account") { AccountScreen() }
            }
        }
    }
}
