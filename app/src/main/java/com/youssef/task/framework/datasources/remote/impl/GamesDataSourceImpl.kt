package com.youssef.task.framework.datasources.remote.impl

import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.services.GamesApi
import javax.inject.Inject

class GamesDataSourceImpl @Inject constructor(private val api: GamesApi) : GamesDataSource {

    override suspend fun getGames(pageNumber: Int, pageSize: Int): Response<List<Game>> =
        api.getGames(pageNumber, pageSize)

    override suspend fun getGameById(gameId: Int): Game = api.getGameById(gameId)

}