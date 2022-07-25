package com.youssef.task.framework.presentation.features.gameDetails

import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.youssef.task.R
import com.youssef.task.business.entities.Game
import com.youssef.task.framework.presentation.features.BaseTest
import com.youssef.task.framework.presentation.features.games.GameHolder
import com.youssef.task.framework.presentation.features.games.GamesAdapter
import com.youssef.task.framework.utils.clickOnItem
import java.lang.Thread.sleep

internal class GameDetailsFragmentTest : BaseTest() {

    override fun startTest() {
        val game = getFirstGame()
        openFirstGame()
        checkViewsData(game)
        testBack()
    }

    private fun checkViewsData(game: Game) {
        onView(withId(R.id.gameName)).check(matches(withText(game.name)))
        onView(withId(R.id.gameRatingCount)).check(
            matches(withText(activity.getString(R.string.game_rating, game.ratingsCount)))
        )
        onView(withId(R.id.gameReleaseDate)).check(matches(withText(game.released)))
        assert(activity.findViewById<RatingBar>(R.id.gameRating).rating == game.rating)
    }

    private fun testBack() {
        pressBack()
        onView(withId(R.id.gamesFragmentLayout)).check(matches(isDisplayed()))
    }

    private fun getFirstGame(): Game {
        sleep(1000)
        return (activity.findViewById<RecyclerView>(R.id.gamesRV).adapter as GamesAdapter).snapshot()
            .first()!!
    }


    private fun openFirstGame() {
        sleep(500)
        clickOnItem<GameHolder>(R.id.gamesRV, R.id.gameItemLayout, 0)
    }
}