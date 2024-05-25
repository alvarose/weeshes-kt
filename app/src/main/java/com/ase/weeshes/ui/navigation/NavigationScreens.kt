package com.ase.weeshes.ui.navigation

import kotlinx.serialization.Serializable


sealed class NavigationScreens() {
    @Serializable
    object Home

    @Serializable
    object Auth

    @Serializable
    data class WishlistDetail(val wishlistId: String)

    @Serializable
    object Categories
}