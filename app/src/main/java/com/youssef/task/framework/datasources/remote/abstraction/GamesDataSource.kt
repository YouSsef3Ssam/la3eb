package com.youssef.task.framework.datasources.remote.abstraction

import com.youssef.task.business.entities.Game

interface GamesDataSource {
    suspend fun getGameById(gameId: String): Game
}