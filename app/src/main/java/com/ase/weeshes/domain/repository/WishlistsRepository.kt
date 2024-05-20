package com.ase.weeshes.domain.repository

import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist
import kotlinx.coroutines.flow.Flow

interface WishlistsRepository {

    suspend fun createWishlist(name: String, icon: String): Boolean

    suspend fun getWishlists(): Flow<List<Wishlist>>

    suspend fun getWishlistById(id: String): Flow<Wishlist?>

    suspend fun getProducts(wishlistId: String): Flow<List<Product>>
}