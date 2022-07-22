package com.youssef.task.di

import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.business.repositories.impl.GamesRepositoryImpl
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.business.usecases.impl.GamesUseCaseImpl
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.impl.GamesDataSourceImpl
import com.youssef.task.framework.datasources.remote.services.GamesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GamesModule {

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): GamesApi =
        retrofit.create(GamesApi::class.java)

    @Provides
    @Singleton
    fun provideGamesDataSource(gamesApi: GamesApi): GamesDataSource =
        GamesDataSourceImpl(gamesApi)

    @Provides
    @Singleton
    fun provideGamesRepository(authDataSource: GamesDataSource): GamesRepository =
        GamesRepositoryImpl(authDataSource)

    @Provides
    @Singleton
    fun provideUserUseCase(gamesRepository: GamesRepository): GamesUseCase =
        GamesUseCaseImpl(gamesRepository)

}