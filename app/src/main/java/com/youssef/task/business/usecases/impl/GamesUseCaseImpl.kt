package com.youssef.task.business.usecases.impl

import androidx.paging.PagingData
import com.youssef.task.business.entities.Game
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesUseCaseImpl @Inject constructor(private val repository: GamesRepository) : GamesUseCase {
    override suspend fun getGames(): Flow<PagingData<Game>> =
        repository.getGames()

    override suspend fun getGameById(gameId: String): Flow<Game> = repository.getGameById(gameId)

}