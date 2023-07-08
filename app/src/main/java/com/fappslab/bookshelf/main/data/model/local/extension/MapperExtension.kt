package com.fappslab.bookshelf.main.data.model.local.extension

import com.fappslab.bookshelf.main.data.model.local.BookEntity
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.libraries.arch.extension.orDash
import com.fappslab.libraries.arch.extension.orFalse

fun Book.toBookEntity(): BookEntity = BookEntity(
    id = id,
    thumbnail = thumbnail,
    smallThumbnail = smallThumbnail,
    authors = authors,
    title = title,
    subtitle = subtitle,
    description = description,
    infoLink = infoLink,
    isFavorite = isFavorite,
    publisher = publisher,
    publishedDate = publishedDate,
    pageCount = pageCount
)

fun BookEntity.toBook(): Book = Book(
    id = id,
    thumbnail = thumbnail.orEmpty(),
    smallThumbnail = smallThumbnail.orEmpty(),
    authors = authors ?: listOf("---"),
    title = title.orEmpty(),
    subtitle = subtitle.orEmpty(),
    description = description.orEmpty(),
    infoLink = infoLink.orEmpty(),
    isFavorite = isFavorite.orFalse(),
    publisher = publisher.orDash(),
    publishedDate = publishedDate.orDash(),
    pageCount = pageCount.orDash()
)

fun List<BookEntity>.toBookList(): List<Book> = map {
    it.toBook()
}

fun List<Book>.toBookEntityList(): List<BookEntity> = map {
    it.toBookEntity()
}
