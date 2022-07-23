package com.youssef.task.business.usecases.abstraction

import androidx.paging.PagingData
import com.youssef.task.business.entities.Game
import kotlinx.coroutines.flow.Flow

interface GamesUseCase {
    suspend fun getGames(): Flow<PagingData<Game>>
    suspend fun getGameById(gameId: Int): Flow<Game>
}