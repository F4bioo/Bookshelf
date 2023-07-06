package com.fappslab.bookshelf.main.data.source.remote

import com.fappslab.bookshelf.main.domain.model.Books
import io.reactivex.Single

interface BookshelfRemoteDataSource {
    fun getBooks(query: String, nextPage: Int): Single<Books>
}
