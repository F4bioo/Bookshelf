package com.fappslab.bookshelf.main.data.model.remote

import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.model.Books
import com.fappslab.libraries.arch.extension.httpsOrEmpty
import com.fappslab.libraries.arch.extension.orDash
import com.google.gson.annotations.SerializedName

data class BooksResponse(
    @SerializedName("totalItems")
    val totalItems: Int?,
    @SerializedName("items")
    val books: List<BookResponse>?
) {

    fun toBooks(): Books =
        Books(
            totalItems = requireNotNull(totalItems),
            bookList = books?.toBookList().orEmpty(),
        )
}

private fun List<BookResponse>.toBookList(): List<Book> = map {
    Book(
        id = requireNotNull(it.id),
        thumbnail = it.volumeInfo?.imageLinks?.thumbnail.httpsOrEmpty(),
        smallThumbnail = it.volumeInfo?.imageLinks?.smallThumbnail.httpsOrEmpty(),
        authors = it.volumeInfo?.authors ?: listOf("---"),
        title = it.volumeInfo?.title.orDash(),
        subtitle = it.volumeInfo?.subtitle.orDash(),
        description = it.volumeInfo?.description.orDash(),
        infoLink = it.volumeInfo?.infoLink.orEmpty(),
        publisher = it.volumeInfo?.publisher.orDash(),
        publishedDate = it.volumeInfo?.publishedDate.orDash(),
        pageCount = it.volumeInfo?.pageCount.orDash()
    )
}

fun BookResponse.toBook(): Book {
    return Book(
        id = id.orEmpty(),
        thumbnail = volumeInfo?.imageLinks?.thumbnail.orEmpty(),
        smallThumbnail = volumeInfo?.imageLinks?.smallThumbnail.orEmpty(),
        authors = volumeInfo?.authors ?: listOf("---"),
        title = volumeInfo?.title.orDash(),
        subtitle = volumeInfo?.subtitle.orDash(),
        description = volumeInfo?.description.orDash(),
        infoLink = volumeInfo?.infoLink.orEmpty(),
        publisher = volumeInfo?.publisher.orDash(),
        publishedDate = volumeInfo?.publishedDate.orDash(),
        pageCount = volumeInfo?.pageCount.orDash()
    )
}
