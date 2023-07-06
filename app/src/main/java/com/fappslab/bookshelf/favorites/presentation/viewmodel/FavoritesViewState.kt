package com.fappslab.bookshelf.favorites.presentation.viewmodel

import com.fappslab.bookshelf.main.domain.model.Book

const val EMPTY_CHILD = 0
const val SUCCESS_CHILD = 1

data class FavoritesViewState(
    val shouldShowDetails: Boolean = false,
    val childPosition: Int = EMPTY_CHILD,
    val bookList: List<Book>? = null,
    val book: Book? = null,
) {

    fun getFavoritesSuccess(bookList: List<Book>) = copy(
        bookList = bookList,
        childPosition = bookList.toChildPosition(),
        shouldShowDetails = false
    )

    private fun List<Book>?.toChildPosition(): Int {
        return if (isNullOrEmpty()) {
            EMPTY_CHILD
        } else SUCCESS_CHILD
    }
}
