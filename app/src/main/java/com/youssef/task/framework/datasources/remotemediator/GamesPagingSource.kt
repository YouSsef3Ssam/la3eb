package com.youssef.task.framework.datasources.remotemediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.youssef.task.business.entities.Game
import com.youssef.task.framework.datasources.remote.services.GamesApi
import com.youssef.task.framework.utils.Constants
import com.youssef.task.framework.utils.Constants.Companion.API_STARTING_PAGE_INDEX
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GamesPagingSource @Inject constructor(private val gamesApi: GamesApi) :
    PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val currentPageNumber = params.key ?: API_STARTING_PAGE_INDEX
            val previousPageNumber =
                if (currentPageNumber == API_STARTING_PAGE_INDEX) null
                else currentPageNumber.minus(1)
            val response = gamesApi.getGames(currentPageNumber, Constants.PAGE_SIZE)
            LoadResult.Page(
                data = response.results,
                prevKey = previousPageNumber,
                nextKey = currentPageNumber.plus(1)
            )
        } catch (exception: IOException) {
            Timber.e(exception)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
