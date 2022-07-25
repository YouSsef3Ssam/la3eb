package com.youssef.task.framework.utils.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.youssef.task.framework.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


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

fun CoroutineScope.launchIdling(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    EspressoIdlingResource.increment()
    val job = this.launch(context, start, block)
    job.invokeOnCompletion {
        EspressoIdlingResource.decrement()
    }
    return job
}