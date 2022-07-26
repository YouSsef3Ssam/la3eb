package com.youssef.task.framework.utils

import com.youssef.task.BuildConfig
import com.youssef.task.framework.utils.Constants.Network.Path.ID

interface Constants {
    object Network {
        const val BASE_URL: String = BuildConfig.HOST

        object GamesEndPoints {
            const val GAMES = "games"
            const val GAME = "$GAMES/{$ID}"
        }

        object Path {
            const val ID = "id"
        }

        object Query {
            const val PAGE_NUMBER = "page"
            const val PAGE_SIZE = "page_size"
            const val ORDERING = "ordering"
            const val ORDERING_VALUE = "-released"
        }

        const val API_KEY = "key"
        const val API_KEY_VALUE = "c3b2ca06630d48a6b1cde858d49de081"
    }

    companion object {
        const val API_STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
        const val UNREACHABLE_STATEMENT = "Unreachable statement"
    }

    object LocalDatabase {
        const val DATABASE_NAME = "games_database"

        object Tables {
            const val GAMES = "games"
            const val GAMES_KEYS = "games_keys"
        }
    }

}