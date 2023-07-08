package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import com.fappslab.bookshelf.stub.BooksFactory.book
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import org.junit.Test

internal class GetFavoriteUseCaseTest {

    private val repository: BookshelfRepository = mockk()
    private val subject = GetFavoriteUseCase(repository)

    @Test
    fun `getFavoriteSuccess Should return a book When use case is invoked`() {
        // Given
        val book = book
        val expectedResult = "F_pICAAAQBAJ"
        every { repository.getFavorite(any()) } returns Maybe.just(book)

        // When
        val result = subject(book = book).test()

        // Then
        result.assertValue { _book ->
            expectedResult == _book.id
        }
        verify { repository.getFavorite(any()) }
    }

    @Test
    fun `getFavoriteFailure Should return default book When use case get failure`() {
        // Given
        val book = book
        val expectedResult = "F_pICAAAQBAJ"
        every { repository.getFavorite(any()) } returns Maybe.error(Throwable())

        // When
        val result = subject(book = book).test()

        // Then
        result.assertValue { _book ->
            expectedResult == _book.id
        }
        verify { repository.getFavorite(any()) }
    }
}
