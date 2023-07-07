package com.fappslab.bookshelf.favorites.presentation.viewmodel

import com.fappslab.bookshelf.R
import com.fappslab.bookshelf.favorites.domain.usecase.FavoritesUseCaseProvider
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.libraries.arch.extension.schedulerOn
import com.fappslab.libraries.arch.viewmodel.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class FavoritesViewModel(
    private val provider: FavoritesUseCaseProvider,
    private val scheduler: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel<FavoritesViewState, FavoritesViewAction>(FavoritesViewState()) {

    init {
        getFavorites()
    }

    private fun getFavorites() {
        provider.getFavoritesUseCase()
            .schedulerOn(scheduler)
            .subscribe({ getFavoritesSuccess(bookList = it) }, {})
            .disposableHandler()
    }

    private fun getFavoritesSuccess(bookList: List<Book>) {
        onState { it.getFavoritesSuccess(bookList = bookList) }
    }

    fun onAdapterItem(id: Int, book: Book) {
        when (id) {
            R.id.card_item -> onState { it.copy(shouldShowDetails = true, book = book) }
            R.id.button_favorite -> onFavorite(book)
        }
    }

    fun onDismissFeedbackDetails() {
        onState { it.copy(shouldShowDetails = false) }
    }

    fun onBackPressed() = state.value.run {
        onAction { FavoritesViewAction.BackPressed }
    }

    fun onFavorite(book: Book) {
        provider.setFavoriteUseCase(book)
            .schedulerOn(scheduler)
            .subscribe({}, {})
            .disposableHandler()
    }
}
