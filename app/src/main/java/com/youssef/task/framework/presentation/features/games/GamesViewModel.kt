package com.youssef.task.framework.presentation.features.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youssef.task.business.entities.Game
import com.youssef.task.business.entities.Response
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.framework.utils.ext.catchError
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val useCase: GamesUseCase) : ViewModel() {
    private val _gamesDataState: MutableLiveData<DataState<Response<List<Game>>>> =
        MutableLiveData()
    val gamesDataState: LiveData<DataState<Response<List<Game>>>> get() = _gamesDataState

    init {
        getGames(1, 10)

    }

    private fun getGames(pageNumber: Int, pageSize: Int) {
        viewModelScope.launch {
            useCase.getGames(1, 10)
                .onStart { _gamesDataState.value = DataState.Loading }
                .catchError { _gamesDataState.value = DataState.Failure(it) }
                .collect { _gamesDataState.value = DataState.Success(it) }
        }
    }

}