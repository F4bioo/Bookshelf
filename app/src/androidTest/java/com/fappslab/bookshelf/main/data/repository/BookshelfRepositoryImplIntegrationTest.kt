package com.fappslab.bookshelf.main.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.RxPagingSource
import androidx.paging.rxjava2.flowable
import com.fappslab.bookshelf.main.data.db.BookshelfDatabase
import com.fappslab.bookshelf.main.data.model.local.extension.toBookEntity
import com.fappslab.bookshelf.main.data.repository.BooksFactory.book
import com.fappslab.bookshelf.main.data.source.local.BookshelfLocalDataSourceImpl
import com.fappslab.bookshelf.main.data.source.remote.BookshelfRemoteDataSourceImpl
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import com.fappslab.libraries.arch.rules.LocalTestRule
import com.fappslab.libraries.arch.rules.RemoteTestRule
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.test.assertNotNull

private const val DURATION = 1L

class BookshelfRepositoryImplIntegrationTest {

    @get:Rule
    val remoteRule = RemoteTestRule()

    @get:Rule
    val localRule = LocalTestRule()

    private lateinit var database: BookshelfDatabase
    private lateinit var subject: BookshelfRepository

    @Before
    fun setUp() {
        database = localRule.createTestDatabase()

        subject = BookshelfRepositoryImpl(
            remoteDataSource = BookshelfRemoteDataSourceImpl(
                service = remoteRule.createTestService()
            ),
            localDataSource = BookshelfLocalDataSourceImpl(
                dao = database.bookshelfDao()
            )
        )
    }

    @Test
    fun getBooks_Should_validate_flowable_data_creation_When_repository_is_invoked() {
        // Given
        remoteRule.toServerSuccessResponse()

        // When
        val result = subject.getBooks(query = "Android")

        // Then
        assertTest(result) {
            assertNotNull(blockingFirst())
        }
    }

    @Test
    fun getFavorites_Should_return_book_list_When_repository_is_invoked() {
        // Given
        val book = book.copy(isFavorite = true).setBook()
        val expectedResult = listOf(book)

        // When
        val result = subject.getFavorites().test()

        // Then
        result.awaitDone(DURATION, SECONDS)
            .assertValue {
                expectedResult == it
            }
    }

    @Test
    fun getFavorite_Should_return_book_When_repository_is_invoked() {
        // Given
        val book = book.setBook()
        val expectedResult = book

        // When
        val result = subject.getFavorite(id = "F_pICAAAQBAJ").test()

        // Then
        result.awaitDone(DURATION, SECONDS)
            .assertValue {
                expectedResult == it
            }
    }

    private fun assertTest(
        pagingSourceFactory: RxPagingSource<Int, Book>,
        block: Flowable<PagingData<Book>>.() -> Unit
    ) {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSourceFactory }
        ).flowable.block()
    }

    private fun Book.setBook(): Book {
        database.bookshelfDao()
            .setBook(toBookEntity())
            .test()
            .assertNoErrors()
            .assertComplete()
        return this
    }
}
