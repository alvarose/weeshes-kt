package com.ase.weeshes.domain.usecase.wishlists

import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.repository.WishlistsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistsUseCase @Inject constructor(private val repository: WishlistsRepository) {
    suspend operator fun invoke(): Flow<List<Wishlist>> = repository.getWishlists()
}