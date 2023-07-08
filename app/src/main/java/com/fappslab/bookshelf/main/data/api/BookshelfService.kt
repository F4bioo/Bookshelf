package com.fappslab.bookshelf.main.data.api

import com.fappslab.bookshelf.main.data.model.remote.BooksResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val MAX_RESULTS = 20

interface BookshelfService {

    @GET("books/v1/volumes")
    fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = MAX_RESULTS,
        @Query("startIndex") startIndex: Int
    ): Single<BooksResponse>
}
