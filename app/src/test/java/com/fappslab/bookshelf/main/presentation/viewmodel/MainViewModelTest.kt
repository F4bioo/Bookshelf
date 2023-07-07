package com.fappslab.bookshelf.main.presentation.viewmodel

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.fappslab.bookshelf.main.domain.usecase.provider.MainUseCaseProvider
import com.fappslab.bookshelf.stub.BooksFactory.book
import com.fappslab.bookshelf.stub.BooksFactory.books
import com.fappslab.libraries.arch.rules.MainCoroutineTestRule
import com.fappslab.libraries.arch.rules.SchedulerTestRule
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val schedulerRule = SchedulerTestRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineTestRule()

    private val pagingData = PagingData.from(books.bookList)
    private val initialState = MainViewState()
    private val provider = MainUseCaseProvider(
        getBooksUseCase = mockk(),
        getFavoriteUseCase = mockk(),
        setFavoriteUseCase = mockk()
    )

    private lateinit var subject: MainViewModel

    @Before
    fun setUp() {
        subject = MainViewModel(
            provider = provider,
            scheduler = schedulerRule.testScheduler
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getBooks Should emit expected state When invoked`() {
        // Given
        val expectedState = initialState.copy(
            shouldShowLoading = false,
            pagingData = pagingData,
            childPosition = 1,
        )
        every { provider.getBooksUseCase(any(), any()) } returns Flowable.just(pagingData)

        // When
        subject.getBooks(query = "Android")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
        }
        verify { provider.getBooksUseCase(any(), any()) }
    }

    @Test
    fun `onFavoritesButton Should emit expected Favorites action When invoked`() {
        // Given
        val expectedAction = MainViewAction.Favorites

        // When
        subject.onFavoritesButton()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
            }
        }
    }

    @Test
    fun `onSearch Should emit expected state and HiddenKeyboard action When invoked`() {
        // Given
        val expectedState = initialState.copy(
            shouldShowLoading = false,
            pagingData = pagingData,
            childPosition = 1,
        )
        val expectedAction = MainViewAction.HiddenKeyboard
        every { provider.getBooksUseCase(any(), any()) } returns Flowable.just(pagingData)

        // When
        subject.onSearch(query = "Android")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
            }
        }
        verify { provider.getBooksUseCase(any(), any()) }
    }

    @Test
    fun `onTryAgain Should emit expected state and TryAgain action When invoked`() {
        // Given
        val expectedState = initialState.copy(shouldShowError = false)
        val expectedAction = MainViewAction.TryAgain

        // When
        subject.onTryAgain()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
            }
        }
    }

    @Test
    fun `onDismissFeedbackError Should emit expected state When invoked`() {
        // Given
        val expectedState = initialState.copy(shouldShowError = false)

        // When
        subject.onDismissFeedbackError()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
        }
    }

    @Test
    fun `onAdapterItem Should emit expected state When invoked`() {
        // Given
        val book = book
        val expectedState = initialState.copy(book = book, shouldShowDetails = true)
        every { provider.getFavoriteUseCase(any()) } returns Maybe.just(book)

        // When
        subject.onAdapterItem(book = book)

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
        }
        verify { provider.getFavoriteUseCase(any()) }
    }


    @Test
    fun `onDismissFeedbackDetails Should emit expected state When invoked`() {
        // Given
        val expectedState = initialState.copy(shouldShowDetails = false)

        // When
        subject.onDismissFeedbackDetails()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
        }
    }

    @Test
    fun `onFavorite Should complete with success When invoked`() {
        // Given
        every { provider.setFavoriteUseCase(any()) } returns Completable.complete()

        // When
        subject.onFavorite(book = book)

        // Then
        verify { provider.setFavoriteUseCase(any()) }
    }

    @Test
    fun `onBackPressed Should emit expected state when invoked`() {
        // Given
        val expectedState = initialState.copy(childPosition = 0, pagingData = PagingData.empty())
        every { provider.getBooksUseCase(any(), any()) } returns Flowable.just(pagingData)
        subject.getBooks(query = "Android")

        // When
        subject.onBackPressed()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
            }
        }
        verify { provider.getBooksUseCase(any(), any()) }
    }

    @Test
    fun `onBackPressed Should emit expected action BackPressed when invoked`() {
        // Given
        val expectedAction = MainViewAction.BackPressed

        // When
        subject.onBackPressed()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
            }
        }
    }

    @Test
    fun `onCombinedLoadState Should handle error When LoadState is Error`() {
        // Given
        val errorMessage = "Network error"
        val loadState = LoadState.Error(Throwable(errorMessage))
        val combinedLoadStates = CombinedLoadStates(
            refresh = loadState,
            append = loadState,
            prepend = loadState,
            source = LoadStates(loadState, loadState, loadState)
        )

        // When
        subject.onCombinedLoadState(combinedLoadStates)

        // Then
        runTest {
            val newState = subject.state.value
            assertTrue(newState.shouldShowError)
            assertFalse(newState.shouldShowLoading)
            assertEquals(errorMessage, newState.errorMessage)
        }
    }

    @Test
    fun `onCombinedLoadState Should handle loading When LoadState is Loading`() {
        // Given
        val loadState = LoadState.Loading
        val combinedLoadStates = CombinedLoadStates(
            refresh = loadState,
            append = loadState,
            prepend = loadState,
            source = LoadStates(loadState, loadState, loadState)
        )

        // When
        subject.onCombinedLoadState(combinedLoadStates)

        // Then
        runTest {
            val newState = subject.state.value
            assertFalse(newState.shouldShowError)
            assertTrue(newState.shouldShowLoading)
        }
    }
}
