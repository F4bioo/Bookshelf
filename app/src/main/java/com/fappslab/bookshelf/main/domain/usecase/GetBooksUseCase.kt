package com.fappslab.bookshelf.main.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import io.reactivex.Flowable

const val PAGE_SIZE = 20

class GetBooksUseCase(
    private val repository: BookshelfRepository
) {

    operator fun invoke(query: String): Flowable<PagingData<Book>> {
        val pagingSource = repository.getBooks(query)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}
