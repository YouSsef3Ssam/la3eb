package com.youssef.task.framework.datasources.remote.impl

import com.youssef.task.business.entities.Game
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.services.GamesApi
import javax.inject.Inject

class GamesDataSourceImpl @Inject constructor(private val api: GamesApi) : GamesDataSource {
    override suspend fun getGameById(gameId: String): Game = api.getGameById(gameId)

}