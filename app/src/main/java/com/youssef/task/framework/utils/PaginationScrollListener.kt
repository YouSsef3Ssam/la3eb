package com.youssef.task.framework.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(
    private var pageNumber: Int,
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    fun isLoading(value: Boolean) {
        isLoading = value
    }

    private var isLastPage = false
    fun isLastPage(value: Boolean) {
        isLastPage = value
    }

    fun isFirstPage() = pageNumber == 1

    fun reload() {
        pageNumber = 1
        loadMoreItems(pageNumber)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                pageNumber++
                loadMoreItems(pageNumber)
            }
        }
    }

    protected abstract fun loadMoreItems(pageNumber: Int)
}

