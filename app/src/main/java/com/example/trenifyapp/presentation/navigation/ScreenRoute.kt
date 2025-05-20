package com.example.trenifyapp.presentation.navigation

sealed class ScreenRoute(val route: String) {
    object AccountSelectionScreen : ScreenRoute("accountSelection")
    object InitialUserDataScreen : ScreenRoute("initialUserData")
    object InitialWorkoutPlan : ScreenRoute("initialWorkoutPlans")
    object InitialExercisesScreen : ScreenRoute("initialExercisesScreen")
    object InitialSettingUpExercisesScreen : ScreenRoute("initialSettingUpExercisesScreen")
    object UserProfileScreen : ScreenRoute("userProfileScreen")
}