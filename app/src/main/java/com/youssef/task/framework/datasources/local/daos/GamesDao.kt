package com.youssef.task.framework.datasources.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youssef.task.framework.datasources.local.entities.GameEntity
import com.youssef.task.framework.utils.Constants.LocalDatabase.Tables

@Dao
interface GamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gamesEntities: List<GameEntity>): List<Long>

    @Query("SELECT * FROM ${Tables.GAMES} ORDER BY released DESC")
    fun getGames(): PagingSource<Int, GameEntity>

    @Query("DELETE FROM ${Tables.GAMES}")
    suspend fun clearGames()
}