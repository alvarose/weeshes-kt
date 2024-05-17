package com.ase.weeshes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ase.weeshes.ui.screens.home.HomeScreen
import com.ase.weeshes.ui.screens.wishlist.detail.WishlistScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.Home
    ) {
        composable<NavigationScreens.Home> {
            HomeScreen { wishlist ->
                navController.navigate(NavigationScreens.WishlistDetail(wishlist.id))
            }
        }

        composable<NavigationScreens.WishlistDetail> {
            WishlistScreen {
                navController.popBackStack()
            }
        }
    }
}