package com.youssef.task.framework.presentation.features.gameDetails

import androidx.fragment.app.viewModels
import com.youssef.task.R
import com.youssef.task.databinding.FragmentGameDetailsBinding
import com.youssef.task.framework.presentation.features.base.BaseFragment
import com.youssef.task.framework.utils.ext.popBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsFragment : BaseFragment<FragmentGameDetailsBinding>() {
    private val viewModel by viewModels<GameDetailsViewModel>()
    override fun bindViews() {
        initUI()
    }

    private fun initUI() {
        binding.gameId = viewModel.gameId
        binding.backBtn.setOnClickListener { popBack() }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_game_details

}