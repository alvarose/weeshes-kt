package com.ase.weeshes.ui.navigation

import kotlinx.serialization.Serializable


sealed class NavigationScreens() {
    @Serializable
    object Home

    @Serializable
    data class WishlistDetail(val wishlistId: String)
}