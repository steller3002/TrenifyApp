package com.example.trenifyapp.presentation.navigation

import com.example.trenifyapp.R

sealed class NavigationItem(val title: String, val iconId: Int, val route: String) {
    object Screen1: NavigationItem("Журнал", R.drawable.journal_icon, "Journal" )
    object Screen2: NavigationItem("Тренировка", R.drawable.dumbbell_icon  , "Workout" )
    object Screen3: NavigationItem("Профиль", R.drawable.account_icon, "Account" )
}