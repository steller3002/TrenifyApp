package com.example.trenifyapp.presentation.navigation

sealed class NavigationRoute(val route: String) {
    object Auth : NavigationRoute("auth")
}