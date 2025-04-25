package com.example.trenifyapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trenifyapp.presentation.screens.AccountSelectionScreen
import com.example.trenifyapp.presentation.screens.MainScreenContent
import com.example.trenifyapp.presentation.screens.SignUpScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = "accountSelection" // Изменяем стартовый экран
    ) {
        // Экран выбора аккаунта
        composable("accountSelection") {
            AccountSelectionScreen(navController = navHostController)
        }

        // Экран регистрации
        composable("signUp") {
            SignUpScreen(navController = navHostController)
        }

        // Основной экран с нижней навигацией
        composable("main") {
            MainScreenContent()
        }
    }
}