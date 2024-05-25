package com.ase.weeshes.data.modules

import android.content.Context
import com.ase.weeshes.data.network.controllers.AuthController
import com.ase.weeshes.data.network.controllers.CategoriesController
import com.ase.weeshes.data.network.controllers.ProductsController
import com.ase.weeshes.data.network.controllers.RemoteConfigController
import com.ase.weeshes.data.network.controllers.WishlistsController
import com.ase.weeshes.domain.repository.AuthRepository
import com.ase.weeshes.domain.repository.CategoriesRepository
import com.ase.weeshes.domain.repository.ProductsRepository
import com.ase.weeshes.domain.repository.RemoteConfigRepository
import com.ase.weeshes.domain.repository.WishlistsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthController(auth)

    @Provides
    fun provideWishlistsRepository(firestore: FirebaseFirestore): WishlistsRepository = WishlistsController(firestore)

    @Provides
    fun provideProductsRepository(firestore: FirebaseFirestore): ProductsRepository = ProductsController(firestore)

    @Provides
    fun provideCategoriesRepository(firestore: FirebaseFirestore): CategoriesRepository = CategoriesController(firestore)

    @Provides
    fun provideRemoteConfigRepository(remoteConfig: FirebaseRemoteConfig, @ApplicationContext appContext: Context): RemoteConfigRepository = RemoteConfigController(remoteConfig, appContext)
}