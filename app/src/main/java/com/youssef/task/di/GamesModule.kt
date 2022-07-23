package com.youssef.task.di

import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.business.repositories.impl.GamesRepositoryImpl
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.business.usecases.impl.GamesUseCaseImpl
import com.youssef.task.framework.datasources.local.AppDatabase
import com.youssef.task.framework.datasources.local.mappers.GamesMapper
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.impl.GamesDataSourceImpl
import com.youssef.task.framework.datasources.remote.services.GamesApi
import com.youssef.task.framework.datasources.remotemediator.GamesRemoteMediator
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
    fun provideGamesApi(retrofit: Retrofit): GamesApi =
        retrofit.create(GamesApi::class.java)


    @Provides
    @Singleton
    fun provideGamesDataSource(gamesApi: GamesApi): GamesDataSource =
        GamesDataSourceImpl(gamesApi)

//    @Provides
//    @Singleton
//    fun provideGamesRemoteMediator(
//        api: GamesApi,
//        database: AppDatabase,
//        gamesMapper: GamesMapper
//    ): GamesRemoteMediator =
//        GamesRemoteMediator(api, database, gamesMapper)

    @Provides
    @Singleton
    fun provideGamesRepository(
        dataSource: GamesDataSource,
        gamesApi: GamesApi,
        database: AppDatabase,
        mapper: GamesMapper
    ): GamesRepository =
        GamesRepositoryImpl(dataSource, gamesApi, database, mapper)

    @Provides
    @Singleton
    fun provideUserUseCase(gamesRepository: GamesRepository): GamesUseCase =
        GamesUseCaseImpl(gamesRepository)

}