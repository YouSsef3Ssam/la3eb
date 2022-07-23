package com.youssef.task.framework.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youssef.task.framework.utils.Constants.LocalDatabase.Tables

@Entity(tableName = Tables.GAMES)
class GameEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "background_image") val image: String?,
    @ColumnInfo(name = "rating") val rating: Float,
    @ColumnInfo(name = "ratings_count") val ratingsCount: Int,
    @ColumnInfo(name = "released") val released: String?,
)