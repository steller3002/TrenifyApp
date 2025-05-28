package com.example.trenifyapp.presentation.navigation

sealed class ScreenRoute(val route: String) {
    object AccountSelectionScreen : ScreenRoute("accountSelection")

    object InitialUserDataScreen : ScreenRoute("initialUserData")

    object InitialWorkoutPlan : ScreenRoute("initialWorkoutPlans")

    object InitialExercisesScreen : ScreenRoute("initialExercisesScreen")

    object InitialSettingUpExercisesScreen : ScreenRoute("initialSettingUpExercisesScreen")

    object UserProfileScreen : ScreenRoute("userProfileScreen/{userId}") {
        fun createRoute(userId: Int) = "userProfileScreen/$userId"
    }

    object WorkoutGenerateScreen : ScreenRoute("workoutScreen/{userId}") {
        fun createRoute(userId: Int) = "workoutScreen/$userId"
    }

    object JournalScreen : ScreenRoute("journalScreen/{userId}") {
        fun createRoute(userId: Int) = "journalScreen/$userId"
    }

    object AccountScreen : ScreenRoute("accountScreen/{userId}") {
        fun createRoute(userId: Int) = "accountScreen/$userId"
    }

    object WorkoutExercisesScreen : ScreenRoute("accountExercisesScreen")
}