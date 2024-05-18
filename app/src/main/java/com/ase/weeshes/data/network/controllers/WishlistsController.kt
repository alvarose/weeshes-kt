package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.data.network.requests.NewWishlistRequest
import com.ase.weeshes.data.network.response.ProductsResponse
import com.ase.weeshes.data.network.response.WishlistsResponse
import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.repository.WishlistsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WishlistsController @Inject constructor(private val firestore: FirebaseFirestore) : WishlistsRepository {

    companion object {
        const val USER_ID = "PeRi3uhriXGNItZWa2Mh"
        const val WEESHES = "weeshes"
        const val WISHLIST = "wishlists"
        const val PRODUCTS = "products"
    }

    override suspend fun createWishlist(name: String, icon: String) {
        val ref = firestore.collection(WEESHES)
            .document(USER_ID)
            .collection(WISHLIST)
            .document()
        ref.set(NewWishlistRequest(ref.id, name, icon)).isSuccessful
    }

    override suspend fun getWishlists(): Flow<List<Wishlist>> = firestore
        .collection(WEESHES)
        .document(USER_ID)
        .collection(WISHLIST)
        .orderBy("id", Query.Direction.ASCENDING)
        .snapshots()
        .map {
            it.toObjects<WishlistsResponse>().map { w -> w.toDomain() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getWishlistById(id: String): Flow<Wishlist?> = firestore
        .collection(WEESHES)
        .document(USER_ID)
        .collection(WISHLIST)
        .document(id)
        .snapshots()
        .map {
            it.toObject<WishlistsResponse>()?.toDomain()
        }.flatMapLatest { wishlist ->
            getProducts(id).map { products ->
                wishlist?.copy(products = products)
            }
        }

    override suspend fun getProducts(wishlistId: String): Flow<List<Product>> = firestore
        .collection(WEESHES)
        .document(USER_ID)
        .collection(WISHLIST)
        .document(wishlistId)
        .collection(PRODUCTS)
        .snapshots()
        .map {
            it.toObjects<ProductsResponse>().map { w -> w.toDomain() }
        }
}