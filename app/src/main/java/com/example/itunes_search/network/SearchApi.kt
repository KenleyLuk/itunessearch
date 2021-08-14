package com.example.itunes_search.network

import com.example.itunes_search.model.SearchResultModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    /**
     * Get the itunes music list
     */
    @GET("search")
    fun searchMusic(@Query("term") searchTerm: CharSequence,
                    @Query("entity") entityType: String): Observable<SearchResultModel>
}