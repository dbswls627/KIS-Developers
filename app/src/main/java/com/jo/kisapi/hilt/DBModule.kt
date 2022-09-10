package com.jo.kisapi.hilt

import android.content.Context
import com.jo.kisapi.AppDatabase
import com.jo.kisapi.TokenTimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)!!

    @Singleton
    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): TokenTimeDao = appDatabase.TokenTimeDao()
}