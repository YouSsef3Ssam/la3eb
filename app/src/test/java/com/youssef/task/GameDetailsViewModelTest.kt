package com.youssef.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.youssef.task.business.entities.Game
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.framework.presentation.features.gameDetails.GameDetailsViewModel
import com.youssef.task.framework.utils.states.DataState
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: GamesUseCase = mockkClass(GamesUseCase::class)
    private lateinit var viewModel: GameDetailsViewModel

    private val gameId = "123"
    private val game = mockkClass(Game::class)
    private val exception = RuntimeException("")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getGameById with success response then return success`() = runBlocking {
        coEvery { useCase.getGameById(gameId) } answers { flow { emit(game) } }
        val savedStateHandle = SavedStateHandle().apply { set("gameId", gameId) }
        viewModel = GameDetailsViewModel(useCase, savedStateHandle)
        assertTrue(viewModel.gameDataState.value is DataState.Success)
        assertEquals(game, (viewModel.gameDataState.value as DataState.Success).data)
    }

    @Test
    fun `getGameById with failure response then return error`() = runBlocking {
        coEvery { useCase.getGameById(gameId) } answers { flow { throw exception } }
        val savedStateHandle = SavedStateHandle().apply { set("gameId", gameId) }
        viewModel = GameDetailsViewModel(useCase, savedStateHandle)
        assertTrue(viewModel.gameDataState.value is DataState.Failure)
        assertEquals(
            exception.message,
            (viewModel.gameDataState.value as DataState.Failure).throwable.message
        )
    }
}