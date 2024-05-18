package com.ase.weeshes.domain.usecase.products

import com.ase.weeshes.domain.repository.ProductsRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val repository: ProductsRepository) {
//    suspend operator fun invoke(): Flow<List<Wishlist>> = repository.addProduct()
}