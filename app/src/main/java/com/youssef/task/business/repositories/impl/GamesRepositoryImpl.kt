package com.youssef.task.business.repositories.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.youssef.task.business.entities.Game
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remotemediator.GamesPagingSource
import com.youssef.task.framework.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val dataSource: GamesDataSource,
    private val gamesPagingSource: GamesPagingSource
) : GamesRepository {

    override suspend fun getGames(): Flow<PagingData<Game>> =
        Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) { gamesPagingSource }
            .flow
            .flowOn(Dispatchers.IO)

    override suspend fun getGameById(gameId: Int): Flow<Game> = flow {
        emit(dataSource.getGameById(gameId))
    }.flowOn(Dispatchers.IO)


}