package com.fappslab.bookshelf.stub

import com.fappslab.bookshelf.main.data.model.remote.BooksResponse
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToModel

private const val BOOKS_SUCCESS_RESPONSE = "books_success_response.json"

object BooksFactory {
    private val booksResponse = readFromJSONToModel<BooksResponse>(BOOKS_SUCCESS_RESPONSE)
    val book = booksResponse.toBooks().bookList.first()
    val books = booksResponse.toBooks()
}
