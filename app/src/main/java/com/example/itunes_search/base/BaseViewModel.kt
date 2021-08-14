package com.example.itunes_search.base

import androidx.lifecycle.ViewModel
import com.example.itunes_search.injection.component.DaggerViewModelInjector
import com.example.itunes_search.injection.component.ViewModelInjector
import com.example.itunes_search.injection.module.NetworkModule
import com.example.itunes_search.module.search.viewModel.SearchListViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is SearchListViewModel -> injector.inject(this)
        }
    }
}