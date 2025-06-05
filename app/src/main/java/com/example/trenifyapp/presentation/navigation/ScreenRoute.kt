package com.example.trenifyapp.presentation.navigation

sealed class ScreenRoute(val route: String) {
    data object AccountSelectionScreen : ScreenRoute("accountSelection")

    data object InitialUserDataScreen : ScreenRoute("initialUserData")

    data object InitialWorkoutPlan : ScreenRoute("initialWorkoutPlans")

    data object InitialExercisesScreen : ScreenRoute("initialExercisesScreen")

    data object InitialSettingUpExercisesScreen : ScreenRoute("initialSettingUpExercisesScreen")

    data object UserProfileScreen : ScreenRoute("userProfileScreen/{userId}") {
        fun createRoute(userId: Int) = "userProfileScreen/$userId"
    }

    data object WorkoutGenerateScreen : ScreenRoute("workoutScreen/{userId}") {
        fun createRoute(userId: Int) = "workoutScreen/$userId"
    }

    data object JournalScreen : ScreenRoute("journalScreen/{userId}") {
        fun createRoute(userId: Int) = "journalScreen/$userId"
    }

    data object AccountScreen : ScreenRoute("accountScreen/{userId}") {
        fun createRoute(userId: Int) = "accountScreen/$userId"
    }

    data object WorkoutExercisesScreen : ScreenRoute("accountExercisesScreen")

    data object WorkoutDetailsScreen : ScreenRoute("workoutDetailsScreen") {
        fun createRoute(userId: Int) = "workoutDetailsScreen/$userId"
    }
}