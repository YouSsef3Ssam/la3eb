package com.youssef.task.business.usecases.abstraction

import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import kotlinx.coroutines.flow.Flow

interface GamesUseCase {
    suspend fun getGames(pageNumber: Int, pageSize: Int): Flow<Response<List<Game>>>
    suspend fun getGameById(gameId: Int): Flow<Game>
}