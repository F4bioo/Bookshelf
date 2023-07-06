package com.fappslab.bookshelf.main.data.source.local

import com.fappslab.bookshelf.main.data.db.BookshelfDao
import com.fappslab.bookshelf.main.data.model.local.extension.toBook
import com.fappslab.bookshelf.main.data.model.local.extension.toBookEntity
import com.fappslab.bookshelf.main.data.model.local.extension.toBookList
import com.fappslab.bookshelf.main.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

class BookshelfLocalDataSourceImpl(
    private val dao: BookshelfDao
) : BookshelfLocalDataSource {

    override fun getFavorites(): Flowable<List<Book>> =
        dao.getFavorites().map { it.toBookList() }

    override fun getFavorite(id: String): Maybe<Book> =
        dao.getBook(id).map { it.toBook() }

    override fun setBook(book: Book): Completable =
        dao.setBook(book.toBookEntity())
}
