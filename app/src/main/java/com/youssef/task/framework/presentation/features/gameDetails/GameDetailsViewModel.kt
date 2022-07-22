package com.youssef.task.framework.presentation.features.gameDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val useCase: GamesUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {
    val gameId: Int = stateHandle.get<Int>("gameId") ?: 0
}