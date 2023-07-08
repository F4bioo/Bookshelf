package com.fappslab.bookshelf.main.data.source.local

import com.fappslab.bookshelf.main.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface BookshelfLocalDataSource {
    fun getFavorites(): Flowable<List<Book>>
    fun getFavorite(id: String): Maybe<Book>
    fun setBook(book: Book): Completable
}
