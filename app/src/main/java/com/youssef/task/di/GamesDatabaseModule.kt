package com.youssef.task.di

import android.content.Context
import androidx.room.Room
import com.youssef.task.framework.datasources.local.AppDatabase
import com.youssef.task.framework.datasources.local.daos.GamesDao
import com.youssef.task.framework.datasources.local.daos.GamesKeysDao
import com.youssef.task.framework.utils.Constants.LocalDatabase.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GamesDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGamesDao(database: AppDatabase): GamesDao = database.gamesDao()

    @Provides
    @Singleton
    fun provideGamesKeysDao(database: AppDatabase): GamesKeysDao = database.gamesKeysDao()

}