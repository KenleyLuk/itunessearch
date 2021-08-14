package com.example.itunes_search.module.search.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.itunes_search.base.BaseViewModel
import com.example.itunes_search.model.ResultModel
import com.example.itunes_search.model.SearchResultModel
import com.example.itunes_search.network.SearchApi
import com.example.itunes_search.utils.extension.async
import com.example.itunes_search.utils.extension.autoHandlerObserveError
import com.example.itunes_search.utils.extension.autoHandlerSubscribeError
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SearchListViewModel: BaseViewModel() {
    @Inject
    lateinit var searchApi: SearchApi

    val resultList = MutableLiveData<List<ResultModel>>()
    var disposable: Disposable? = null

    fun callSearchAPI(char: CharSequence): Observable<SearchResultModel> {
        return searchApi.searchMusic(char, "musicTrack")
            .async()
            .doOnNext{ APIModel ->
                resultList.value = APIModel.results
            }
            .doOnComplete{
                Log.d("doOnComplete","true")
            }
            .doOnSubscribe{
                Log.d("doOnSubscribe","true")
                disposable = it
            }
            .doOnError{
                Log.d("doOnError","true")
            }
            .autoHandlerObserveError()
            .autoHandlerSubscribeError {
                disposable?.dispose()
                Log.d("autoHandlerError","true")
            }
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }
}