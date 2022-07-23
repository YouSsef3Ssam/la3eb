package com.youssef.task.business.repositories.abstraction

import androidx.paging.PagingData
import com.youssef.task.business.entities.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGames(): Flow<PagingData<Game>>
    suspend fun getGameById(gameId: String): Flow<Game>
}