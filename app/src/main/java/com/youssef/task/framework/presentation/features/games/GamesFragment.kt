package com.youssef.task.framework.presentation.features.games

import androidx.fragment.app.viewModels
import com.youssef.task.R
import com.youssef.task.databinding.FragmentGamesBinding
import com.youssef.task.framework.presentation.features.base.BaseFragment
import com.youssef.task.framework.utils.ext.navigateTo
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>() {
    private val viewModel by viewModels<GamesViewModel>()

    override fun bindViews() {
        initUI()
        subscribeOnViewObservers()
    }

    private fun initUI() {
        binding.text.setOnClickListener { navigateTo(GamesFragmentDirections.openGameDetails(1)) }

    }

    private fun subscribeOnViewObservers() {
        viewModel.gamesDataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> showMessage(it.data.count.toString())
                is DataState.Failure -> handleError(it.throwable)
                DataState.Loading -> showMessage("Loading")
            }
        }
    }

    override fun getLayoutResId() = R.layout.fragment_games

}