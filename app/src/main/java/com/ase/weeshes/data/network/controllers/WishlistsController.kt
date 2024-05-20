package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.core.firebase.FirestoreValues
import com.ase.weeshes.data.network.requests.newWishlistRequest
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

    override suspend fun createWishlist(name: String, icon: String): Boolean {
        val ref = firestore.collection(FirestoreValues.WEESHES_COLL)
            .document(FirestoreValues.USER_ID)
            .collection(FirestoreValues.WISHLISTS_COLL)
            .document()
        return ref.set(newWishlistRequest(ref.id, name, icon)).isSuccessful
    }

    override suspend fun getWishlists(): Flow<List<Wishlist>> = firestore
        .collection(FirestoreValues.WEESHES_COLL)
        .document(FirestoreValues.USER_ID)
        .collection(FirestoreValues.WISHLISTS_COLL)
        .orderBy("id", Query.Direction.ASCENDING)
        .snapshots()
        .map {
            it.toObjects<WishlistsResponse>().map { w -> w.toDomain() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getWishlistById(id: String): Flow<Wishlist?> = firestore
        .collection(FirestoreValues.WEESHES_COLL)
        .document(FirestoreValues.USER_ID)
        .collection(FirestoreValues.WISHLISTS_COLL)
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
        .collection(FirestoreValues.WEESHES_COLL)
        .document(FirestoreValues.USER_ID)
        .collection(FirestoreValues.WISHLISTS_COLL)
        .document(wishlistId)
        .collection(FirestoreValues.PRODUCTS_COLL)
        .snapshots()
        .map {
            it.toObjects<ProductsResponse>().map { w -> w.toDomain() }
        }
}