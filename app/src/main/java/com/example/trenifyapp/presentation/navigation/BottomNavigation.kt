package com.example.trenifyapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun BottomNavigation(
    navController: NavController,
    userId: Int,
) {
    val listItems = listOf(
        NavigationItem.JournalNavigationItem,
        NavigationItem.WorkoutNavigationItem,
        NavigationItem.AccountNavigationItem
    )
    NavigationBar (
        Modifier.background(Color.White)
    ){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach { item->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(
                        when (item) {
                            is NavigationItem.JournalNavigationItem ->
                                ScreenRoute.JournalScreen.createRoute(userId)
                            is NavigationItem.WorkoutNavigationItem ->
                                ScreenRoute.WorkoutGenerateScreen.createRoute(userId)
                            is NavigationItem.AccountNavigationItem ->
                                ScreenRoute.AccountScreen.createRoute(userId)
                        }
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon"
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Orange,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}