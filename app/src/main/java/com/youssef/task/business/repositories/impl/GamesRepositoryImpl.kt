package com.youssef.task.business.repositories.impl

import androidx.paging.*
import com.youssef.task.business.entities.Game
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.framework.datasources.local.AppDatabase
import com.youssef.task.framework.datasources.local.mappers.GamesMapper
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.services.GamesApi
import com.youssef.task.framework.datasources.remotemediator.GamesRemoteMediator
import com.youssef.task.framework.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val dataSource: GamesDataSource,
    private val gamesApi: GamesApi,
    private val database: AppDatabase,
    private val mapper: GamesMapper
) : GamesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGames(): Flow<PagingData<Game>> =
        Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = Constants.PAGE_SIZE
            ),
            remoteMediator = GamesRemoteMediator(gamesApi, database, mapper),
            pagingSourceFactory = { database.gamesDao().getGames() }
        )
            .flow
            .map { pagingData ->
                pagingData.map { mapper.mapFromEntity(it) }
            }
            .flowOn(Dispatchers.IO)

    override suspend fun getGameById(gameId: String): Flow<Game> = flow {
        emit(dataSource.getGameById(gameId))
    }.flowOn(Dispatchers.IO)


}