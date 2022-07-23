package com.youssef.task.framework.presentation.features.gameDetails

import androidx.lifecycle.*
import com.youssef.task.business.entities.Game
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.framework.utils.ext.catchError
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val useCase: GamesUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {
    val gameId: String? = stateHandle.get<String>("gameId")

    private val _gameDataState: MutableLiveData<DataState<Game>> =
        MutableLiveData()
    val gameDataState: LiveData<DataState<Game>> get() = _gameDataState

    init {
        gameId?.let { getGames(gameId) }
    }

    private fun getGames(gameId: String) {
        viewModelScope.launch {
            useCase.getGameById(gameId)
                .onStart { _gameDataState.value = DataState.Loading }
                .catchError { _gameDataState.value = DataState.Failure(it) }
                .collect { _gameDataState.value = DataState.Success(it) }
        }
    }
}