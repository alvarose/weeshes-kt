package com.ase.weeshes.domain.repository

interface ProductsRepository {
    suspend fun createProduct(wishlistId: String, name: String, link: String, category: String)
}