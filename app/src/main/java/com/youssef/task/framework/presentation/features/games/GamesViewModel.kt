package com.youssef.task.framework.presentation.features.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.youssef.task.business.entities.Game
import com.youssef.task.business.usecases.abstraction.GamesUseCase
import com.youssef.task.framework.utils.ext.catchError
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val useCase: GamesUseCase) : ViewModel() {
    private val _gamesDataState: MutableLiveData<DataState<PagingData<Game>>> =
        MutableLiveData()
    val gamesDataState: LiveData<DataState<PagingData<Game>>> get() = _gamesDataState

    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch {
            useCase.getGames().cachedIn(viewModelScope)
                .catchError { _gamesDataState.value = DataState.Failure(it) }
                .collectLatest { _gamesDataState.value = DataState.Success(it) }
        }
    }

}