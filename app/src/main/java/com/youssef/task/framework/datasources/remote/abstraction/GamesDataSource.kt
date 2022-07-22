package com.youssef.task.framework.datasources.remote.abstraction

import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response

interface GamesDataSource {
    suspend fun getGames(pageNumber: Int, pageSize: Int): Response<List<Game>>
    suspend fun getGameById(gameId: Int): Game
}