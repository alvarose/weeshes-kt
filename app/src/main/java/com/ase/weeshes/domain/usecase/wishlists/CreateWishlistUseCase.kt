package com.ase.weeshes.domain.usecase.wishlists

import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.repository.WishlistsRepository
import javax.inject.Inject

class CreateWishlistUseCase @Inject constructor(private val repository: WishlistsRepository) {
    suspend operator fun invoke(name: String, icon: String) = repository.createWishlist(name, icon)
}