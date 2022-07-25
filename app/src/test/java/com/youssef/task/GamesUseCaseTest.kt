package com.youssef.task

import androidx.paging.PagingData
import com.youssef.task.business.entities.Game
import com.youssef.task.business.repositories.abstraction.GamesRepository
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.business.usecases.impl.GamesUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GamesUseCaseTest {

    private val repository: GamesRepository = mockkClass(GamesRepository::class)
    private lateinit var useCase: GamesUseCase

    private val game = mockkClass(Game::class)
    private val games = listOf(game)
    private val gamesPagingData = PagingData.from(games)
    private val exception = RuntimeException("Can't get the game")

    @Before
    fun setUp() {
        useCase = GamesUseCaseImpl(repository)
    }

    @Test
    fun `getGames with success response then return success`() = runBlocking {
        coEvery { repository.getGames() } answers { flow { emit(gamesPagingData) } }
        val response = useCase.getGames()

        var success: PagingData<Game>? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(gamesPagingData, success)
        val expectedGames = mutableListOf<Game>().apply {
            games.map { this.add(it) }
        }
        assertEquals(expectedGames, games)
    }

    @Test
    fun `getGames with failure response then return error`() = runBlocking {
        coEvery { repository.getGames() } answers { flow { throw exception } }
        val response = useCase.getGames()

        var success: PagingData<Game>? = null
        var error: Throwable? = null

        response
            .catch { error = it }
            .collect { success = it }

        assertNull(success)
        assertNotNull(error)
        assertEquals(exception.message, error!!.message)
    }

    @Test
    fun `getGameById with success response then return success`() = runBlocking {
        coEvery { repository.getGameById(any()) } answers { flow { emit(game) } }
        val response = useCase.getGameById("123")

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
        coEvery { repository.getGameById(any()) } answers { flow { throw exception } }
        val response = useCase.getGameById("123")

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