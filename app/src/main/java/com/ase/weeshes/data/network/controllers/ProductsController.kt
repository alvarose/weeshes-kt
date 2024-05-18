package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.repository.ProductsRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProductsController @Inject constructor(private val firestore: FirebaseFirestore) : ProductsRepository {
    override suspend fun addProduct(product: Product) {
        TODO("Not yet implemented")
    }
}