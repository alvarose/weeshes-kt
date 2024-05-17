package com.ase.weeshes.domain.usecase

import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.repository.WishlistRepository
import javax.inject.Inject

class CreateWishlistUseCase @Inject constructor(private val repository: WishlistRepository) {
    suspend operator fun invoke(wishlist: Wishlist) = repository.createWishlist(wishlist)
}