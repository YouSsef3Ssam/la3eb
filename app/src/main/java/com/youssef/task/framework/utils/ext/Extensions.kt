package com.youssef.task.framework.utils.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import timber.log.Timber


fun Fragment.navigateTo(direction: NavDirections) {
    try {
        findNavController().navigateSafe(direction)
    } catch (e: Exception) {
        Timber.e(e)
    }
}

fun Fragment.popBack() {
    try {
        findNavController().popBackStack()
    } catch (e: Exception) {
        Timber.e(e)
    }
}


fun NavController.navigateSafe(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun LoadState.isLoading(): Boolean = this is LoadState.Loading
fun LoadState.error(errorHandler: (Throwable) -> Unit) {
    if (this is LoadState.Error) errorHandler.invoke(error)
}