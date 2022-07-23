package com.youssef.task.framework.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youssef.task.framework.utils.Constants.LocalDatabase.Tables

@Entity(tableName = Tables.GAMES_KEYS)
class GameKeyEntity(
    @PrimaryKey val gameId: String,
    @ColumnInfo(name = "prevKey") val prevKey: Int?,
    @ColumnInfo(name = "nextKey") val nextKey: Int?
)