package com.youssef.task.framework.datasources.local.mappers

import com.youssef.task.business.entities.Game
import com.youssef.task.framework.datasources.local.entities.GameEntity
import javax.inject.Inject

class GamesMapper @Inject constructor() : EntityMapper<GameEntity, Game> {
    override fun mapFromEntity(entity: GameEntity): Game = with(entity) {
        Game(
            id = id,
            name = name,
            image = image,
            rating = rating,
            ratingsCount = ratingsCount,
            released = released
        )
    }

    override fun mapToEntity(response: Game): GameEntity = with(response) {
        GameEntity(
            id = id,
            name = name,
            image = image,
            rating = rating,
            ratingsCount = ratingsCount,
            released = released
        )
    }
}