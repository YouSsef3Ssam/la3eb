package com.youssef.task

import com.youssef.task.business.entities.Game
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.business.repositories.impl.GamesRepositoryImpl
import com.youssef.task.framework.datasources.local.AppDatabase
import com.youssef.task.framework.datasources.local.daos.GamesDao
import com.youssef.task.framework.datasources.local.entities.GameEntity
import com.youssef.task.framework.datasources.local.mappers.GamesMapper
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.services.GamesApi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GamesRepositoryTest {

    private val dataSource: GamesDataSource = mockkClass(GamesDataSource::class)
    private val api: GamesApi = mockkClass(GamesApi::class)
    private val gamesDao: GamesDao = mockkClass(GamesDao::class)
    private val database: AppDatabase = mockkClass(AppDatabase::class)
    private val mapper: GamesMapper = mockkClass(GamesMapper::class)

    private lateinit var repository: GamesRepository

    private val game = mockkClass(Game::class)
    private val gameEntity = mockkClass(GameEntity::class)
    private val exception = RuntimeException("Can't get the game")

    @Before
    fun setUp() {
        repository = GamesRepositoryImpl(dataSource, api, database, mapper)
        every { database.gamesDao() } returns gamesDao
        every { mapper.mapToEntity(game) } returns gameEntity
    }


    @Test
    fun `getGameById with success response then return success`() = runBlocking {
        coEvery { dataSource.getGameById(any()) } returns game
        val response = repository.getGameById("123")

        var success: Game? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(game, success)
    }

    @Test
    fun `getGameById with failure response then return error`() = runBlocking {
        coEvery { dataSource.getGameById(any()) } answers { throw exception }
        val response = repository.getGameById("123")

        var success: Game? = null
        var error: Throwable? = null

        response
            .catch { error = it }
            .collect { success = it }

        assertNull(success)
        assertNotNull(error)
        assertEquals(exception.message, error!!.message)
    }


}