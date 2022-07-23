package com.youssef.task.framework.presentation.features.games

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.youssef.task.R
import com.youssef.task.business.entities.Game
import com.youssef.task.databinding.FragmentGamesBinding
import com.youssef.task.framework.presentation.callback.OnItemClickListener
import com.youssef.task.framework.presentation.features.base.BaseFragment
import com.youssef.task.framework.utils.ext.navigateTo
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>() {
    private val viewModel by viewModels<GamesViewModel>()
    private var adapter: GamesAdapter? = null

    override fun bindViews() {
        initUI()
        subscribeOnViewObservers()
    }

    private fun initUI() {
        adapter = GamesAdapter()
        adapter?.listen(object : OnItemClickListener<Game> {
            override fun onItemClicked(item: Game) {
                navigateTo(GamesFragmentDirections.openGameDetails(item.id))
            }
        })
        binding.gamesRV.adapter = adapter
    }

    private fun subscribeOnViewObservers() {
        viewModel.gamesDataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> viewLifecycleOwner.lifecycleScope.launch {
                    adapter?.submitData(it.data)
                }
                is DataState.Failure -> handleError(it.throwable)
                DataState.Loading -> showMessage("Loading")
            }
        }
    }

    override fun getLayoutResId() = R.layout.fragment_games

    override fun onDestroyView() {
        adapter = null
        binding.gamesRV.adapter = null
        super.onDestroyView()
    }
}