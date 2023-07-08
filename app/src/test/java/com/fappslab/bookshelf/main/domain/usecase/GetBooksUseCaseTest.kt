package com.fappslab.bookshelf.main.domain.usecase

import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import com.fappslab.bookshelf.stub.BooksFactory.books
import com.fappslab.bookshelf.stub.PagingSourceFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBooksUseCaseTest {

    private val repository: BookshelfRepository = mockk()
    private val subject = GetBooksUseCase(repository)

    @Test
    fun `getBooks Should validate flowable data creation When use case is invoked`() {
        // Given
        val bookList = books.bookList
        val scope = CoroutineScope(context = UnconfinedTestDispatcher())
        every { repository.getBooks(any()) } returns PagingSourceFactory.create(bookList)

        // When
        val result = subject(query = "Android", scope = scope)

        // Then
        result.firstOrError()
            .test()
            .assertNoErrors()
            .assertValueCount(1)
        verify { repository.getBooks(any()) }
    }
}
