package com.example.itunes_search.model

data class SearchResultModel(
    var resultCount: Int,
    var results: List<ResultModel>
)