package com.youssef.task.framework.presentation.features.games

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.youssef.task.R
import com.youssef.task.framework.presentation.features.BaseTest
import com.youssef.task.framework.utils.clickOnItem
import org.junit.Test
import java.lang.Thread.sleep

internal class GamesFragmentTest : BaseTest() {

    override fun startTest() {
        onView(withId(R.id.gamesFragmentLayout)).check(matches(isDisplayed()))
        sleep(500)
        onView(withId(R.id.gamesRV)).check(matches(isDisplayed()))
    }

    @Test
    fun testBack() {
        openFirstGame()
        onView(withId(R.id.gameDetailsFragmentLayout)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.gamesFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun openGameDetails() {
        openFirstGame()
        onView(withId(R.id.gameDetailsFragmentLayout)).check(matches(isDisplayed()))
    }

    private fun openFirstGame() {
        sleep(500)
        clickOnItem<GameHolder>(R.id.gamesRV, R.id.gameItemLayout, 0)
    }
}