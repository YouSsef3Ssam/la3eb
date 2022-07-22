package com.youssef.task.framework.presentation.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.youssef.task.business.entities.Game


class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Game, newItem: Game) =
        oldItem == newItem
}