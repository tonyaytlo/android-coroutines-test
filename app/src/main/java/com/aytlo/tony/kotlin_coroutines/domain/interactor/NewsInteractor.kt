package com.aytlo.tony.kotlin_coroutines.domain.interactor

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.model.SearchResult
import com.aytlo.tony.kotlin_coroutines.domain.core.UseCase
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import javax.inject.Inject

class NewsInteractor
@Inject constructor(private val newsRepository: NewsRepository) :
    UseCase<SearchResult, UseCase.None>() {

    override suspend fun run(params: None): Result<SearchResult, Throwable> {
        return newsRepository.search()
    }
}