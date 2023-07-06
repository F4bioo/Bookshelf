package com.fappslab.bookshelf.main.domain.repository

import androidx.paging.rxjava2.RxPagingSource
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.model.Books
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface BookshelfRepository {
    fun getBooks(query: String): RxPagingSource<Int, Book>
    fun getFavorites(): Flowable<List<Book>>
    fun getFavorite(id: String): Maybe<Book>
    fun setBook(book: Book): Completable
}
