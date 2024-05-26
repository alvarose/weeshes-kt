package com.ase.weeshes.ui.navigation

import kotlinx.serialization.Serializable


sealed class NavigationScreens {
    @Serializable
    data object Home : NavigationScreens()

    @Serializable
    data object Auth : NavigationScreens()

    @Serializable
    data object Version : NavigationScreens()

    @Serializable
    data class WishlistDetail(val wishlistId: String) : NavigationScreens()

    @Serializable
    data object Categories : NavigationScreens()
}