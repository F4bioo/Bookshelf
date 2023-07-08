package com.fappslab.bookshelf.main.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val PAGE_SIZE = 20

class GetBooksUseCase(
    private val repository: BookshelfRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(query: String, scope: CoroutineScope): Flowable<PagingData<Book>> {
        val pagingSource = repository.getBooks(query)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { pagingSource }
        ).flowable.cachedIn(scope)
    }
}
