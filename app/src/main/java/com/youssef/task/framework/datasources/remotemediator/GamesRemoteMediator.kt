package com.youssef.task.framework.datasources.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.youssef.task.framework.datasources.local.AppDatabase
import com.youssef.task.framework.datasources.local.entities.GameEntity
import com.youssef.task.framework.datasources.local.entities.GameKeyEntity
import com.youssef.task.framework.datasources.local.mappers.GamesMapper
import com.youssef.task.framework.datasources.remote.services.GamesApi
import com.youssef.task.framework.utils.Constants.Companion.API_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GamesRemoteMediator @Inject constructor(
    private val api: GamesApi,
    private val database: AppDatabase,
    private val gamesMapper: GamesMapper
) : RemoteMediator<Int, GameEntity>() {

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: API_STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(remoteKeys != null)
                    nextKey
                }
            }
            val apiResponse =
                api.getGames(pageNumber = page, pageSize = state.config.pageSize)
            val games = apiResponse.results
            val endOfPaginationReached = apiResponse.results.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.gamesKeysDao().clearGamesKeys()
                    database.gamesDao().clearGames()
                }
                val prevKey =
                    if (page == API_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = games.map { game ->
                    GameKeyEntity(gameId = game.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.gamesDao().insertAll(games.map { gamesMapper.mapToEntity(it) })
                database.gamesKeysDao().insertAll(keys)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameEntity>): GameKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { game ->
                database.gamesKeysDao().getKeyByGameId(game.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameEntity>): GameKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { gameId ->
                database.gamesKeysDao().getKeyByGameId(gameId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GameEntity>): GameKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { game ->
                database.gamesKeysDao().getKeyByGameId(game.id)
            }
    }

}
