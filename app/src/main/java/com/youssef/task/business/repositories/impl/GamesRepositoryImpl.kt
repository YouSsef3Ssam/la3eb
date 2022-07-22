package com.youssef.task.business.repositories.impl

import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(private val dataSource: GamesDataSource) :
    GamesRepository {
    override suspend fun getGames(pageNumber: Int, pageSize: Int): Flow<Response<List<Game>>> =
        flow {
            emit(dataSource.getGames(pageNumber, pageSize))
        }.flowOn(Dispatchers.IO)

    override suspend fun getGameById(gameId: Int): Flow<Game> = flow {
        emit(dataSource.getGameById(gameId))
    }.flowOn(Dispatchers.IO)


}