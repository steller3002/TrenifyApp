package com.example.trenifyapp.presentation.navigation

import com.example.trenifyapp.R

sealed class NavigationItem(val title: String, val iconId: Int, val route: String) {
    object JournalNavigationItem: NavigationItem("Журнал", R.drawable.journal_icon, ScreenRoute.JournalScreen.route )
    object WorkoutNavigationItem: NavigationItem("Тренировка", R.drawable.dumbbell_icon  , ScreenRoute.WorkoutScreen.route )
    object AccountNavigationItem: NavigationItem("Профиль", R.drawable.account_icon, ScreenRoute.AccountScreen.route )
}