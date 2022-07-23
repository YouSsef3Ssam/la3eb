package com.youssef.task.framework.presentation.features.gameDetails

import androidx.fragment.app.viewModels
import com.youssef.task.R
import com.youssef.task.databinding.FragmentGameDetailsBinding
import com.youssef.task.framework.presentation.features.base.BaseFragment
import com.youssef.task.framework.utils.ext.popBack
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsFragment : BaseFragment<FragmentGameDetailsBinding>() {
    private val viewModel by viewModels<GameDetailsViewModel>()
    override fun bindViews() {
        initUI()
        subscribeOnViewObservers()
    }

    private fun initUI() {
        binding.backBtn.setOnClickListener { popBack() }
    }

    private fun subscribeOnViewObservers() {
        viewModel.gameDataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    binding.loading = false
                    binding.game = it.data
                }
                is DataState.Failure -> {
                    binding.loading = false
                    handleError(it.throwable)
                }
                DataState.Loading -> binding.loading = true
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_game_details

}