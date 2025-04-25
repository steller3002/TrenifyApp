package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trenifyapp.presentation.navigation.Account
import com.example.trenifyapp.presentation.navigation.BottomNavigation
import com.example.trenifyapp.presentation.navigation.Journal
import com.example.trenifyapp.presentation.navigation.Workout

@Composable
fun MainScreenContent() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        // Применяем padding от Scaffold
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = "Journal"
            ) {
                composable("Journal") { Journal() }
                composable("Workout") { Workout() }
                composable("Account") { Account() }
            }
        }
    }
}