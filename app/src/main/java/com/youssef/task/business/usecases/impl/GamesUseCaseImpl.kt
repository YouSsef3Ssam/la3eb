package com.youssef.task.business.usecases.impl

import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesUseCaseImpl @Inject constructor(private val repository: GamesRepository) : GamesUseCase {
    override suspend fun getGames(pageNumber: Int, pageSize: Int): Flow<Response<List<Game>>> =
        repository.getGames(pageNumber, pageSize)

    override suspend fun getGameById(gameId: Int): Flow<Game> = repository.getGameById(gameId)

}