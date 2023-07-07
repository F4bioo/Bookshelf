package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import com.fappslab.bookshelf.stub.BooksFactory.books
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import org.junit.Test

class GetFavoritesUseCaseTest {

    private val repository: BookshelfRepository = mockk()
    private val subject = GetFavoritesUseCase(repository)

    @Test
    fun `getFavoritesSuccess Should return book list When use case is invoked`() {
        // Given
        val expectedResult = books.bookList
        every { repository.getFavorites() } returns Flowable.just(expectedResult)

        // When
        val result = subject().test()

        // Then
        result.assertValue {
            expectedResult == it
        }
        verify { repository.getFavorites() }
    }

    @Test
    fun `getFavoritesFailure Should throw exception When use case get failure`() {
        // Given
        val expectedResult = "Some error"
        every { repository.getFavorites() } returns Flowable.error(Throwable(expectedResult))

        // When
        val result = subject().test()

        // Then
        result.assertError { cause ->
            expectedResult == cause.message
        }
        verify { repository.getFavorites() }
    }
}
