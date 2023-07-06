package com.fappslab.bookshelf.favorites.presentation.viewmodel

import com.fappslab.bookshelf.R
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.domain.usecase.GetFavoritesUseCase
import com.fappslab.bookshelf.main.domain.usecase.SetFavoriteUseCase
import com.fappslab.libraries.arch.extension.schedulerOn
import com.fappslab.libraries.arch.viewmodel.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@Suppress("UnusedPrivateMember")
class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val scheduler: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel<FavoritesViewState, FavoritesViewAction>(FavoritesViewState()) {

    init {
        getFavorites()
    }

    private fun getFavorites() {
        getFavoritesUseCase()
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
        setFavoriteUseCase(book)
            .schedulerOn(scheduler)
            .subscribe({}, {})
            .disposableHandler()
    }
}
