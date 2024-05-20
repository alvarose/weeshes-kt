package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.core.firebase.FirestoreValues
import com.ase.weeshes.data.network.requests.newProductRequest
import com.ase.weeshes.domain.repository.ProductsRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProductsController @Inject constructor(private val firestore: FirebaseFirestore) : ProductsRepository {
    override suspend fun createProduct(wishlistId: String, name: String, link: String, category: String) {
        val ref = firestore.collection(FirestoreValues.WEESHES_COLL)
            .document(FirestoreValues.USER_ID)
            .collection(FirestoreValues.WISHLISTS_COLL)
            .document(wishlistId)
            .collection(FirestoreValues.PRODUCTS_COLL)
            .document()

        ref.set(newProductRequest(ref.id, name, link, category)).isSuccessful
    }
}