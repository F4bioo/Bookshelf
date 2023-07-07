package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import com.fappslab.bookshelf.stub.BooksFactory.book
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Test

class SetFavoriteUseCaseTest {

    private val repository: BookshelfRepository = mockk()
    private val subject = SetFavoriteUseCase(repository)

    @Test
    fun `setFavoriteSuccess Should complete with success When use case is invoked`() {
        // Given
        every { repository.setBook(any()) } returns Completable.complete()

        // When
        val result = subject(book = book).test()

        // Then
        result.assertComplete()
        verify { repository.setBook(any()) }
    }

    @Test
    fun `setFavoriteFailure Should throw exception When use case get failure`() {
        // Given
        val expectedResult = "Some error"
        every { repository.setBook(any()) } returns Completable.error(Throwable(expectedResult))

        // When
        val result = subject(book = book).test()

        // Then
        result.assertError { cause ->
            expectedResult == cause.message
        }
        verify { repository.setBook(any()) }
    }
}
