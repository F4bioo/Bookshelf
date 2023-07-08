package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import io.reactivex.Completable

class SetFavoriteUseCase(
    private val repository: BookshelfRepository
) {

    operator fun invoke(book: Book): Completable =
        repository.setBook(book)
}
