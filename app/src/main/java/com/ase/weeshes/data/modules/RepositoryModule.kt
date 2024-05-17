package com.ase.weeshes.data.modules

import com.ase.weeshes.data.network.controllers.WishlistController
import com.ase.weeshes.domain.repository.WishlistRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideWishlistRepository(firestore: FirebaseFirestore): WishlistRepository = WishlistController(firestore)

}