package com.fappslab.bookshelf.main.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.bookshelf.main.domain.model.Book

const val CHIP_CHILD = 0
const val SUCCESS_CHILD = 1

data class MainViewState(
    val childPosition: Int = CHIP_CHILD,
    val shouldShowLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val shouldShowDetails: Boolean = false,
    val pagingData: PagingData<Book>? = null,
    val errorMessage: String? = null,
    val book: Book? = null
) {

    fun getBooksSuccess(pagingData: PagingData<Book>) = copy(
        shouldShowLoading = false,
        childPosition = SUCCESS_CHILD,
        pagingData = pagingData
    )
}
