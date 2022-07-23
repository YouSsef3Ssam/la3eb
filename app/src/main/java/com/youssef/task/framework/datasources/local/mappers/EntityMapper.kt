package com.youssef.task.framework.datasources.local.mappers

interface EntityMapper <Entity, Response>{

    fun mapFromEntity(entity: Entity): Response

    fun mapToEntity(response: Response): Entity
}