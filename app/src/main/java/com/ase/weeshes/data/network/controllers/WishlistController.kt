package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.data.network.response.WishlistsResponse
import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.repository.WishlistRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WishlistController @Inject constructor(private val firestore: FirebaseFirestore) : WishlistRepository {

    override suspend fun createWishlist(wishlist: Wishlist) {
        firestore.collection("wishlists").add(wishlist.toRequest())
    }

    override suspend fun getWishlists(): Flow<List<Wishlist>> {
        return firestore
            .collection("wishlists")
            .orderBy("id", Query.Direction.ASCENDING)
            .snapshots()
            .map {
                it.toObjects<WishlistsResponse>().map { w -> w.toDomain() }
            }
    }

    override suspend fun getWishlistById(id: String): Flow<Wishlist?> {
        return firestore
            .collection("wishlists")
            .document(id)
            .snapshots()
            .map { it.toObject<WishlistsResponse>()?.toDomain() }
    }

    override suspend fun addProduct(product: Product) {
        TODO("Not yet implemented")
    }
}