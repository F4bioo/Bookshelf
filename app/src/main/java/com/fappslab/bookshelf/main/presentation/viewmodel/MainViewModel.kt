package com.fappslab.bookshelf.main.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.usecase.GetBooksUseCase
import com.fappslab.bookshelf.main.domain.usecase.GetFavoriteUseCase
import com.fappslab.bookshelf.main.domain.usecase.SetFavoriteUseCase
import com.fappslab.libraries.arch.extension.schedulerOn
import com.fappslab.libraries.arch.viewmodel.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val scheduler: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel<MainViewState, MainViewAction>(MainViewState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getBooks(query: String) {
        getBooksUseCase(query)
            .cachedIn(viewModelScope)
            .subscribe { getBooksSuccess(pagingData = it) }
            .disposableHandler()
    }

    private fun getBooksSuccess(pagingData: PagingData<Book>) {
        onState { it.getBooksSuccess(pagingData = pagingData) }
    }

    fun onFavoritesButton() {
        onAction { MainViewAction.Favorites }
    }

    fun onSearch(query: String) {
        onAction { MainViewAction.HiddenKeyboard }
        getBooks(query)
    }

    fun onTryAgain() {
        onDismissFeedbackError()
        onAction { MainViewAction.TryAgain }
    }

    fun onDismissFeedbackError() {
        onState { it.copy(shouldShowError = false) }
    }

    fun onAdapterItem(book: Book) {
        getFavoriteUseCase(book)
            .schedulerOn(scheduler)
            .subscribe(::openDetails)
            .disposableHandler()
    }

    private fun openDetails(book: Book) {
        onState { it.copy(book = book, shouldShowDetails = true) }
    }

    fun onDismissFeedbackDetails() {
        onState { it.copy(shouldShowDetails = false) }
    }

    fun onFavorite(book: Book) {
        setFavoriteUseCase(book)
            .schedulerOn(scheduler)
            .subscribe({}, {})
            .disposableHandler()
    }

    fun onBackPressed() = state.value.run {
        if (childPosition != CHIP_CHILD) {
            onState { it.copy(childPosition = CHIP_CHILD, pagingData = PagingData.empty()) }
        } else onAction { MainViewAction.BackPressed }
    }

    fun onCombinedLoadState(loadState: CombinedLoadStates) {
        val isLoading = loadState.refresh is LoadState.Loading
        onState { state ->
            loadState.handleError()?.let {
                state.copy(
                    shouldShowError = true,
                    shouldShowLoading = false,
                    errorMessage = it.message,
                )
            } ?: state.copy(shouldShowLoading = isLoading)
        }
    }

    private fun CombinedLoadStates.handleError(): Throwable? {
        val loadStates = listOf(
            source.refresh, source.prepend, source.append, // PagingSource LoadState
            refresh, append, prepend // RemoteMediator LoadState
        )

        val errorState = loadStates
            .filterIsInstance<LoadState.Error>()
            .firstOrNull()

        return errorState?.error
    }
}
