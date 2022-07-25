package com.youssef.task

import androidx.paging.*
import androidx.room.withTransaction
import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import com.youssef.task.framework.datasources.local.AppDatabase
import com.youssef.task.framework.datasources.local.daos.GamesDao
import com.youssef.task.framework.datasources.local.daos.GamesKeysDao
import com.youssef.task.framework.datasources.local.entities.GameEntity
import com.youssef.task.framework.datasources.local.mappers.GamesMapper
import com.youssef.task.framework.datasources.remote.services.GamesApi
import com.youssef.task.framework.datasources.remotemediator.GamesRemoteMediator
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalPagingApi::class)
@RunWith(MockitoJUnitRunner::class)
class GamesRemoteMediatorTest {

    private val game = mockkClass(Game::class)
    private val gameEntity = mockkClass(GameEntity::class)
    private val mockGamesResponse = Response(listOf(game))
    private val mockApi: GamesApi = mockkClass(GamesApi::class)
    private val mockDb: AppDatabase = mockkClass(AppDatabase::class)
    private val gamesDao: GamesDao = mockkClass(GamesDao::class)
    private val gamesKeyDao: GamesKeysDao = mockkClass(GamesKeysDao::class)
    private val mapper: GamesMapper = mockkClass(GamesMapper::class)
    private lateinit var gamesRemoteMediator: GamesRemoteMediator

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gamesRemoteMediator = GamesRemoteMediator(mockApi, mockDb, mapper)

        mockkStatic("androidx.room.RoomDatabaseKt")
        val transactionLambda = slot<suspend () -> R>()
        coEvery { mockDb.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }

        coEvery { gamesDao.clearGames() } returns Unit
        coEvery { gamesDao.insertAll(any()) } returns emptyList()

        coEvery { gamesKeyDao.clearGamesKeys() } returns Unit
        coEvery { gamesKeyDao.insertAll(any()) } returns emptyList()

        every { mockDb.gamesDao() } returns gamesDao
        every { mockDb.gamesKeysDao() } returns gamesKeyDao

        every { game.id } returns "123"
        every { mapper.mapToEntity(game) } returns gameEntity
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        coEvery { mockApi.getGames(any(), any(), any()) } answers { mockGamesResponse }
        val pagingState = PagingState<Int, GameEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = gamesRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        coEvery { mockApi.getGames(any(), any(), any()) } answers { Response(emptyList()) }
        val pagingState = PagingState<Int, GameEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = gamesRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

}