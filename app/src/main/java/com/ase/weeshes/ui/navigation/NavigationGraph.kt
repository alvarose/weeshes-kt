package com.ase.weeshes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ase.weeshes.ui.screens.auth.AuthScreen
import com.ase.weeshes.ui.screens.categories.CategoriesScreen
import com.ase.weeshes.ui.screens.home.HomeScreen
import com.ase.weeshes.ui.screens.home.wishlist.WishlistScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.Auth
    ) {
        composable<NavigationScreens.Auth> {
            AuthScreen {
                navController.navigate(NavigationScreens.Home)
            }
        }

        composable<NavigationScreens.Home> {
            HomeScreen(
                onWishlistClick = { wishlist ->
                    navController.navigate(NavigationScreens.WishlistDetail(wishlist.id))
                },
                onCategoriesClick = {
                    navController.navigate(NavigationScreens.Categories)
                }
            )
        }

        composable<NavigationScreens.WishlistDetail> {
            WishlistScreen {
                navController.popBackStack()
            }
        }

        composable<NavigationScreens.Categories> {
            CategoriesScreen {
                navController.popBackStack()
            }
        }
    }
}