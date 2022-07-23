package com.youssef.task.framework.datasources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youssef.task.framework.datasources.local.entities.GameKeyEntity
import com.youssef.task.framework.utils.Constants.LocalDatabase.Tables

@Dao
interface GamesKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gamesKeys: List<GameKeyEntity>): List<Long>

    @Query("SELECT * FROM ${Tables.GAMES_KEYS} WHERE gameId = :gameId")
    suspend fun getKeyByGameId(gameId: String): GameKeyEntity

    @Query("DELETE FROM ${Tables.GAMES_KEYS}")
    suspend fun clearGamesKeys()

}