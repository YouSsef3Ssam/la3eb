package com.youssef.task.framework.datasources.remote.services

import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import com.youssef.task.framework.utils.Constants.Network.Companion.GamesEndPoints
import com.youssef.task.framework.utils.Constants.Network.Companion.Path.ID
import com.youssef.task.framework.utils.Constants.Network.Companion.Query.PAGE_NUMBER
import com.youssef.task.framework.utils.Constants.Network.Companion.Query.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET(GamesEndPoints.GAMES)
    suspend fun getGames(
        @Query(PAGE_NUMBER) pageNumber: Int,
        @Query(PAGE_SIZE) pageSize: Int
    ): Response<List<Game>>

    @GET(GamesEndPoints.GAME)
    suspend fun getGameById(@Path(ID) gameId: Int): Game

}