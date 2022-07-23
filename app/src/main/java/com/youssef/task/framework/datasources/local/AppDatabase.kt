package com.youssef.task.framework.datasources.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.youssef.task.framework.datasources.local.daos.GamesDao
import com.youssef.task.framework.datasources.local.daos.GamesKeysDao
import com.youssef.task.framework.datasources.local.entities.GameEntity
import com.youssef.task.framework.datasources.local.entities.GameKeyEntity


@Database(entities = [GameEntity::class, GameKeyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
    abstract fun gamesKeysDao(): GamesKeysDao
}