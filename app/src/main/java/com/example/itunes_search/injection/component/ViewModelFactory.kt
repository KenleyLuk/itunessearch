package com.example.itunes_search.injection.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunes_search.module.search.viewModel.SearchListViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SearchListViewModel::class.java) -> @Suppress("UNCHECKED_CAST")
            return SearchListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}