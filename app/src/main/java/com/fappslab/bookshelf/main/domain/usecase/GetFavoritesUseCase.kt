package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import io.reactivex.Flowable

class GetFavoritesUseCase(
    private val repository: BookshelfRepository
) {

    operator fun invoke(): Flowable<List<Book>> =
        repository.getFavorites()
}
