package com.ase.weeshes.data.modules

import android.content.Context
import com.ase.weeshes.WeeshesApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context = WeeshesApp().applicationContext

}