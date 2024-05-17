package com.ase.weeshes.domain.usecase

import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistByIdUseCase @Inject constructor(private val repository: WishlistRepository) {
    suspend operator fun invoke(id: String): Flow<Wishlist?> = repository.getWishlistById(id)
}