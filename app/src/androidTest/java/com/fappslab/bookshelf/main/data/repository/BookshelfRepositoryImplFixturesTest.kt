package com.fappslab.bookshelf.main.data.repository

import androidx.annotation.VisibleForTesting
import com.fappslab.bookshelf.main.data.model.remote.BooksResponse
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToModel
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToString
import com.fappslab.libraries.arch.rules.RemoteTestRule
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

private const val BOOKS_SUCCESS_RESPONSE = "books_success_response.json"
private const val BOOKS_FAILURE_RESPONSE = "books_failure_response.json"

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun RemoteTestRule.toServerSuccessResponse() {
    val body = readFromJSONToString(BOOKS_SUCCESS_RESPONSE)
    mockWebServerResponse(body, HttpURLConnection.HTTP_OK)
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun RemoteTestRule.toServerFailureResponse() {
    val body = readFromJSONToString(BOOKS_FAILURE_RESPONSE)
    mockWebServerResponse(body, HttpsURLConnection.HTTP_BAD_REQUEST)
}

object BooksFactory {
    private val booksResponse = readFromJSONToModel<BooksResponse>(BOOKS_SUCCESS_RESPONSE)
    val book = booksResponse.toBooks().bookList.first()
    val books = booksResponse.toBooks()
}
