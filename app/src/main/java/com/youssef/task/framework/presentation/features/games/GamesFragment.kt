package com.youssef.task.framework.presentation.features.games

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.youssef.task.R
import com.youssef.task.business.entities.Game
import com.youssef.task.databinding.FragmentGamesBinding
import com.youssef.task.framework.presentation.callback.OnItemClickListener
import com.youssef.task.framework.presentation.features.base.BaseFragment
import com.youssef.task.framework.utils.Constants
import com.youssef.task.framework.utils.EspressoIdlingResource
import com.youssef.task.framework.utils.ext.error
import com.youssef.task.framework.utils.ext.isLoading
import com.youssef.task.framework.utils.ext.navigateTo
import com.youssef.task.framework.utils.states.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>() {
    private val viewModel by viewModels<GamesViewModel>()

    @Inject
    lateinit var adapter: GamesAdapter

    override fun bindViews() {
        initUI()
        subscribeOnViewObservers()

    }

    private fun initUI() {
        adapter.listen(object : OnItemClickListener<Game> {
            override fun onItemClicked(item: Game) {
                navigateTo(GamesFragmentDirections.openGameDetails(item.id))
            }
        })
        binding.gamesRV.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            loadState.refresh.apply {
                binding.loading = this.isLoading()
                this.error { handleError(it) }
            }
        }

    }

    private fun subscribeOnViewObservers() {
        viewModel.gamesDataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> submitData(it.data)
                is DataState.Failure -> handleError(it.throwable)
                DataState.Loading -> Timber.d(Constants.UNREACHABLE_STATEMENT)
            }
        }
    }

    private fun submitData(data: PagingData<Game>) {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.submitData(data)
            EspressoIdlingResource.decrement()
        }
    }

    override fun getLayoutResId() = R.layout.fragment_games
}