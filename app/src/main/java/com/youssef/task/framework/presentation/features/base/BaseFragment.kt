package com.youssef.task.framework.presentation.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.youssef.task.R
import com.youssef.task.business.entities.errors.ErrorTypes
import com.youssef.task.framework.utils.ext.getMessage
import com.youssef.task.framework.utils.ext.getType

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract fun bindViews()

    @LayoutRes
    abstract fun getLayoutResId(): Int

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        bindViews()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    protected fun handleError(throwable: Throwable) {
        val errorMessage = when (val type = throwable.getType()) {
            is ErrorTypes.HttpError -> type.getMessage().text ?: getInternetConnectionErrorMessage()
            else -> getInternetConnectionErrorMessage()
        }
        handleError(errorMessage)
    }

    private fun getInternetConnectionErrorMessage() =
        requireContext().getString(R.string.please_check_your_internet_connection)

    protected fun handleError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }

    protected fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}