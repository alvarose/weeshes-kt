package com.ase.weeshes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ase.weeshes.ui.screens.auth.AuthScreen
import com.ase.weeshes.ui.screens.categories.CategoriesScreen
import com.ase.weeshes.ui.screens.home.HomeScreen
import com.ase.weeshes.ui.screens.home.wishlist.WishlistScreen
import com.ase.weeshes.ui.screens.splash.SplashViewModel

@Composable
fun NavigationGraph(
    viewModel: SplashViewModel,
) {
    val navController = rememberNavController()
    val startDestination by remember { mutableStateOf(viewModel.checkDestination()) }

    NavHost(
        navController = navController,
        startDestination = startDestination
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