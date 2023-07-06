package com.fappslab.bookshelf.main.data.source.remote

import com.fappslab.bookshelf.main.data.api.BookshelfService
import com.fappslab.bookshelf.main.domain.model.Books
import com.fappslab.libraries.arch.extension.parseHttpError
import io.reactivex.Single

class BookshelfRemoteDataSourceImpl(
    private val service: BookshelfService
) : BookshelfRemoteDataSource {

    override fun getBooks(query: String, nextPage: Int): Single<Books> =
        service.getBooks(query = query, startIndex = nextPage)
            .map { it.toBooks() }
            .parseHttpError()
}
