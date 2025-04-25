package com.example.trenifyapp.presentation.navigation

import com.example.trenifyapp.R

sealed class NavigationItem(val title: String, val iconId: Int, val route: String) {
    object Screen1: NavigationItem("Journal", R.drawable.journal_icon, "Journal" )
    object Screen2: NavigationItem("Workout", R.drawable.dumbbell_icon  , "Workout" )
    object Screen3: NavigationItem("Account", R.drawable.account_icon, "Account" )
}