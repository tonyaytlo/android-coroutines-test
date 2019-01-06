package com.aytlo.tony.kotlin_coroutines.domain.interactor

import androidx.lifecycle.LiveData
import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.model.News
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class NewsFeedInteractorImpl
@Inject constructor(private val newsPaginator: Paginator<News>) :
    UseCase<UseCase.None, NewsFeedInteractorImpl.Action>(), NewsFeedInteractor {

    override fun loadNextPage() {
        invoke(Action {
            newsPaginator.loadNextPage()
            Result.Success(None())
        }) {}
    }

    override fun reload() {
        invoke(Action {
            newsPaginator.reload()
            Result.Success(None())
        }) {}
    }

    override fun liveData(): LiveData<PaginationState<News>> = newsPaginator.liveData()

    override suspend fun run(params: Action): Result<None, Throwable> {
        Timber.d("%s run", Thread.currentThread().name)

        return params.action()
    }

    class Action(val action: () -> Result<None, Throwable>)
}