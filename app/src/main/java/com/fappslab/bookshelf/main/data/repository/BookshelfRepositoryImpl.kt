package com.fappslab.bookshelf.main.data.repository

import androidx.paging.rxjava2.RxPagingSource
import com.fappslab.bookshelf.main.data.paging.BookshelfPagingSource
import com.fappslab.bookshelf.main.data.source.local.BookshelfLocalDataSource
import com.fappslab.bookshelf.main.data.source.remote.BookshelfRemoteDataSource
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

class BookshelfRepositoryImpl(
    private val remoteDataSource: BookshelfRemoteDataSource,
    private val localDataSource: BookshelfLocalDataSource
) : BookshelfRepository {

    override fun getBooks(query: String): RxPagingSource<Int, Book> =
        BookshelfPagingSource(query, remoteDataSource)

    override fun getFavorites(): Flowable<List<Book>> =
        localDataSource.getFavorites()

    override fun getFavorite(id: String): Maybe<Book> =
        localDataSource.getFavorite(id)

    override fun setBook(book: Book): Completable =
        localDataSource.setBook(book)
}
