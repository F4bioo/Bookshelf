package com.fappslab.bookshelf.main.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.fappslab.bookshelf.main.data.source.remote.BookshelfRemoteDataSource
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.model.Books
import com.fappslab.libraries.arch.extension.orZero
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BookshelfPagingSource(
    private val query: String,
    private val remoteDataSource: BookshelfRemoteDataSource,
) : RxPagingSource<Int, Book>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Book>> {
        val currentPage = params.key.orZero()
        return remoteDataSource.getBooks(query, currentPage)
            .subscribeOn(Schedulers.io())
            .map { currentPage.toLoadResult(books = it) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.inc() ?: anchorPage?.nextKey?.dec()
        }
    }

    private fun Int.toLoadResult(books: Books): LoadResult<Int, Book> {
        return LoadResult.Page(
            prevKey = null,
            nextKey = books.toNextKey(page = this),
            data = books.bookList,
        )
    }

    private fun Books.toNextKey(page: Int): Int? {
        val nextPage = page.inc()
        return if (nextPage < totalItems) {
            nextPage
        } else null
    }
}
