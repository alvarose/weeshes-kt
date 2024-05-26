package com.ase.weeshes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ase.weeshes.ui.screens.auth.AuthScreen
import com.ase.weeshes.ui.screens.categories.CategoriesScreen
import com.ase.weeshes.ui.screens.home.HomeScreen
import com.ase.weeshes.ui.screens.splash.SplashViewModel
import com.ase.weeshes.ui.screens.version.VersionScreen
import com.ase.weeshes.ui.screens.wishlist.WishlistScreen

@Composable
fun NavigationGraph(
    viewModel: SplashViewModel,
) {
    val navController = rememberNavController()
    val startDestination by viewModel.startDestination.collectAsState()

    startDestination?.let {
        NavHost(
            navController = navController,
            startDestination = it
        ) {
            composable<NavigationScreens.Auth> {
                AuthScreen {
                    navController.navigate(NavigationScreens.Home) {
                        navController.navigate(NavigationScreens.Home) { popUpToTop(navController) }
                    }
                }
            }

            composable<NavigationScreens.Home> {
                HomeScreen(
                    onWishlistClick = { wishlist ->
                        navController.navigate(NavigationScreens.WishlistDetail(wishlist.id))
                    },
                    onCategoriesClick = {
                        navController.navigate(NavigationScreens.Categories)
                    },
                    onLoggedOut = {
                        navController.navigate(NavigationScreens.Auth) { popUpToTop(navController) }
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

            composable<NavigationScreens.Version> {
                VersionScreen()
            }
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}