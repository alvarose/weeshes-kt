package com.ase.weeshes.core.di

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    fun provideFirebaseAnalytics() = Firebase.analytics

    @Singleton
    fun provideFirebaseFirestore() = Firebase.firestore

}