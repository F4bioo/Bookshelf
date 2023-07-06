package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import io.reactivex.Maybe

class GetFavoriteUseCase(
    private val repository: BookshelfRepository
) {

    operator fun invoke(book: Book): Maybe<Book> {
        return repository.getFavorite(book.id)
            .defaultIfEmpty(book)
            .onErrorReturnItem(book)
    }
}
