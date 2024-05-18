package com.ase.weeshes.data.modules

import com.ase.weeshes.data.network.controllers.CategoriesController
import com.ase.weeshes.data.network.controllers.ProductsController
import com.ase.weeshes.data.network.controllers.WishlistsController
import com.ase.weeshes.domain.repository.CategoriesRepository
import com.ase.weeshes.domain.repository.ProductsRepository
import com.ase.weeshes.domain.repository.WishlistsRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideWishlistsRepository(firestore: FirebaseFirestore): WishlistsRepository = WishlistsController(firestore)

    @Provides
    fun provideProductsRepository(firestore: FirebaseFirestore): ProductsRepository = ProductsController(firestore)

    @Provides
    fun provideCategoriesRepository(firestore: FirebaseFirestore): CategoriesRepository = CategoriesController(firestore)
}