package com.ase.weeshes.domain.repository

import com.ase.weeshes.domain.model.Product

interface ProductsRepository {
    suspend fun addProduct(product: Product)
}