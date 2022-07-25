package com.youssef.task

import com.youssef.task.business.entities.Game
import com.youssef.task.framework.datasources.remote.abstraction.GamesDataSource
import com.youssef.task.framework.datasources.remote.impl.GamesDataSourceImpl
import com.youssef.task.framework.datasources.remote.services.GamesApi
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GamesDataSourceTest {
    private lateinit var dataSource: GamesDataSource
    private val api = mockkClass(GamesApi::class)
    private val game = mockkClass(Game::class)

    private val expectedGetGameSuccessResult = game
    private val expectedFailureResult = RuntimeException("Can't get the game")


    @Before
    fun setUp() {
        dataSource = GamesDataSourceImpl(api)
    }

    @Test
    fun `getGameById with success response then return success`() = runBlocking {
        coEvery { api.getGameById(any()) } answers { expectedGetGameSuccessResult }
        val response = dataSource.getGameById("123")

        assertNotNull(response)
        assertEquals(expectedGetGameSuccessResult, response)
    }

    @Test
    fun `getGameById with failure response then return error`() = runBlocking {
        coEvery { api.getGameById(any()) } throws expectedFailureResult
        var response: RuntimeException? = null
        try {
            dataSource.getGameById("123")
        } catch (e: RuntimeException) {
            response = e
        }

        assertNotNull(response)
        assertEquals(expectedFailureResult, response)
        assertEquals(expectedFailureResult.message, response?.message)
    }


}