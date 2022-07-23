package com.youssef.task.framework.presentation.features.games

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.youssef.task.business.entities.Game
import com.youssef.task.framework.presentation.callback.OnItemClickListener
import com.youssef.task.framework.presentation.diffCallback.GameDiffCallback

class GamesAdapter : PagingDataAdapter<Game, GameHolder>(GameDiffCallback()) {
    private var onItemClickListener: OnItemClickListener<Game>? = null
    fun listen(onItemClickListener: OnItemClickListener<Game>) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder =
        GameHolder.from(parent, onItemClickListener)
}