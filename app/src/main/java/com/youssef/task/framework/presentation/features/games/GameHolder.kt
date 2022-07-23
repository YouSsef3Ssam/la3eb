package com.youssef.task.framework.presentation.features.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youssef.task.business.entities.Game
import com.youssef.task.databinding.ItemGameBinding
import com.youssef.task.framework.presentation.callback.OnItemClickListener

class GameHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(game: Game) {
        binding.game = game
    }

    companion object {
        fun from(parent: ViewGroup, onItemClickListener: OnItemClickListener<Game>?): GameHolder {
            val binding =
                ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.listener = onItemClickListener
            return GameHolder(binding)
        }
    }
}