package com.ase.weeshes.domain.usecase.products

import com.ase.weeshes.domain.repository.ProductsRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(wishlistId: String, name: String, link: String, category: String) = repository.createProduct(wishlistId, name, link, category)
}