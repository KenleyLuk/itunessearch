package com.example.itunes_search.injection.component

import com.example.itunes_search.injection.module.NetworkModule
import com.example.itunes_search.module.search.viewModel.SearchListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 *Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified SearchListViewModel.
     * @param searchListViewModel SearchListViewModel in which to inject the dependencies
     */
    fun inject(searchListViewModel: SearchListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}