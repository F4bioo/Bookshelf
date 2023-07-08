package com.fappslab.bookshelf.stub

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.fappslab.bookshelf.main.domain.model.Book
import io.reactivex.Single

class PagingSourceFactory {

    private fun creates(bookList: List<Book>) = object : RxPagingSource<Int, Book>() {

        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Book>> {
            return Single.just(createLoadResult())
        }

        override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
            return state.anchorPosition
        }

        private fun createLoadResult(): LoadResult.Page<Int, Book> =
            LoadResult.Page(data = bookList, prevKey = null, nextKey = 20)
    }

    companion object {
        fun create(bookList: List<Book>): RxPagingSource<Int, Book> {
            return PagingSourceFactory().creates(bookList)
        }
    }
}
