package com.ase.weeshes.domain.repository

import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {

    suspend fun createWishlist(wishlist: Wishlist)

    suspend fun getWishlists(): Flow<List<Wishlist>>

    suspend fun getWishlistById(id: String): Flow<Wishlist?>
    
    suspend fun addProduct(product: Product)
}