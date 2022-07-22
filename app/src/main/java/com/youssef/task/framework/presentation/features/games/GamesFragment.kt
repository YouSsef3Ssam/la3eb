package com.youssef.task.framework.presentation.features.games

import com.youssef.task.R
import com.youssef.task.databinding.FragmentGamesBinding
import com.youssef.task.framework.presentation.features.base.BaseFragment
import com.youssef.task.framework.utils.ext.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>() {

    override fun bindViews() {
        initUI()
    }

    private fun initUI() {
        binding.text.setOnClickListener { navigateTo(GamesFragmentDirections.openGameDetails(1)) }

    }

    override fun getLayoutResId() = R.layout.fragment_games

}