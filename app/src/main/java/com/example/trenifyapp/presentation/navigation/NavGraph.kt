package com.example.trenifyapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.trenifyapp.presentation.screens.AccountSelectionScreen
import com.example.trenifyapp.presentation.screens.InitialUserDataScreen
import com.example.trenifyapp.presentation.screens.InitialWorkoutPlansScreen
import com.example.trenifyapp.presentation.screens.InitialExercisesScreen
import com.example.trenifyapp.presentation.screens.InitialSettingUpExercisesScreen
import com.example.trenifyapp.presentation.screens.UserProfileScreen
import com.example.trenifyapp.presentation.viewmodels.AccountSelectionViewModel
import com.example.trenifyapp.presentation.viewmodels.SignUpViewModel
import com.example.trenifyapp.presentation.viewmodels.UserProfileViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoute.AccountSelectionScreen.route
    ) {
        composable(ScreenRoute.AccountSelectionScreen.route) {
            val viewModel: AccountSelectionViewModel = hiltViewModel()
            AccountSelectionScreen(
                navigateToInitialUserDataScreen = {
                    navHostController.navigate(NavigationRoute.Auth.route)
                },
                navigateToUserProfileScreen = {
                    navHostController.navigate(ScreenRoute.UserProfileScreen.route)
                },
                viewModel = viewModel
            )
        }

        navigation(
            startDestination = ScreenRoute.InitialUserDataScreen.route,
            route = NavigationRoute.Auth.route
        ) {
            composable(ScreenRoute.InitialUserDataScreen.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navHostController)
                InitialUserDataScreen(
                    viewModel = viewModel,
                    navigateToWorkoutPlansScreen = {
                        navHostController.navigate(ScreenRoute.InitialWorkoutPlan.route)
                    }
                )
            }

            composable(ScreenRoute.InitialWorkoutPlan.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navHostController)
                InitialWorkoutPlansScreen(
                    viewModel = viewModel,
                    navigateToWorkoutStatsScreen = {
                        navHostController.navigate(ScreenRoute.InitialExercisesScreen.route)
                    }
                )
            }

            composable(ScreenRoute.InitialExercisesScreen.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navHostController)
                InitialExercisesScreen(
                    viewModel = viewModel,
                    navigateToSettingUpExercisesScreen = {
                        navHostController.navigate(ScreenRoute.InitialSettingUpExercisesScreen.route)
                    }
                )
            }

            composable(ScreenRoute.InitialSettingUpExercisesScreen.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navHostController)
                InitialSettingUpExercisesScreen (
                    viewModel = viewModel,
                    navigateToAccountsScreen = {
                        navHostController.navigate(ScreenRoute.AccountSelectionScreen.route)
                    }
                )
            }
        }

        composable(ScreenRoute.UserProfileScreen.route) { backStateEntry ->
            val viewModel: UserProfileViewModel = hiltViewModel()
//            val userId = backStateEntry.arguments?.getString("userId")?.toIntOrNull()

//            userId?.let {
//                UserProfileScreen(
//                    userId = userId,
//                    viewModel = viewModel)
//            }
            UserProfileScreen(
                userId = 0,
                viewModel = viewModel
            )
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}