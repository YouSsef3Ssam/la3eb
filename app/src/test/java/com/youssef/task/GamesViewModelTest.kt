package com.youssef.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import com.youssef.task.business.entities.Game
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.framework.presentation.features.games.GamesViewModel
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
class GamesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: GamesUseCase = mockkClass(GamesUseCase::class)
    private lateinit var viewModel: GamesViewModel

    private val game = mockkClass(Game::class)
    private val games = listOf(game)
    private val gamesPagingData = PagingData.from(games)

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
    fun `getGames with success response then return success`() = runBlocking {
        coEvery { useCase.getGames() } answers { flow { emit(gamesPagingData) } }
        viewModel = GamesViewModel(useCase)
        assertTrue(viewModel.gamesDataState.value is DataState.Success)
        val expected = mutableListOf<Game>().apply { gamesPagingData.map { this.add(it) } }
        val responseValue = (viewModel.gamesDataState.value as DataState.Success).data
        val actual =
            mutableListOf<Game>().apply { responseValue.map { this.add(it) } }
        assertEquals(expected, actual)
    }
}