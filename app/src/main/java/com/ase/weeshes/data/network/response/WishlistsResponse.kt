package com.ase.weeshes.data.network.response

import com.ase.weeshes.domain.model.Wishlist

data class WishlistsResponse(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
) {
    fun toDomain(): Wishlist = Wishlist(
        id = id,
        name = name,
        icon = icon
    )
}
