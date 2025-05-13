package com.example.trenifyapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trenifyapp.presentation.screens.AccountSelectionScreen
import com.example.trenifyapp.presentation.screens.InitialUserDataScreen
import com.example.trenifyapp.presentation.screens.InitialWorkoutPlansScreen
import com.example.trenifyapp.presentation.screens.InitialExercisesScreen
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
        startDestination = "accountSelection"
    ) {
        composable("accountSelection") {
            val viewModel: AccountSelectionViewModel = hiltViewModel()
            AccountSelectionScreen(
                navigateToInitialUserDataScreen = {
                    navHostController.navigate("initialUserData")
                },
                navigateToUserProfileScreen = { userId ->
                    navHostController.navigate("userProfileScreen/$userId")
                },
                viewModel = viewModel
            )
        }

        composable("initialUserData") { backStackEntry ->
            val viewModel: SignUpViewModel = hiltViewModel(backStackEntry)
            InitialUserDataScreen(
                viewModel = viewModel,
                navigateToWorkoutPlansScreen = {
                    navHostController.navigate("initialWorkoutPlans")
                }
            )
        }

        composable("initialWorkoutPlans") { backStackEntry ->
            val viewModel: SignUpViewModel =
                if (navHostController.previousBackStackEntry != null)
                    hiltViewModel(
                        navHostController.previousBackStackEntry!!
                    )
            else hiltViewModel()
            InitialWorkoutPlansScreen(
                viewModel = viewModel,
                navigateToWorkoutStatsScreen = {
                    navHostController.navigate("initialWorkoutStats")
                }
            )
        }

        composable("initialWorkoutStats") { backStackEntry ->
            val viewModel: SignUpViewModel =
                if (navHostController.previousBackStackEntry != null)
                    hiltViewModel(
                        navHostController.previousBackStackEntry!!
                    )
                else hiltViewModel()
            InitialExercisesScreen(
                viewModel = viewModel,
                navigateToAccountsScreen = {
                    navHostController.navigate("accountSelection")
                }
            )
        }

        composable("userProfileScreen/{userId}") { backStateEntry ->
            val viewModel: UserProfileViewModel = hiltViewModel()
            val userId = backStateEntry.arguments?.getString("userId")?.toIntOrNull()

            userId?.let {
                UserProfileScreen(
                    userId = userId,
                    viewModel = viewModel)
            }
        }
    }
}