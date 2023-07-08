package com.fappslab.bookshelf.main.domain.usecase.provider

import com.fappslab.bookshelf.main.domain.usecase.GetBooksUseCase
import com.fappslab.bookshelf.main.domain.usecase.GetFavoriteUseCase
import com.fappslab.bookshelf.main.domain.usecase.SetFavoriteUseCase

data class MainUseCaseProvider(
    val getBooksUseCase: GetBooksUseCase,
    val getFavoriteUseCase: GetFavoriteUseCase,
    val setFavoriteUseCase: SetFavoriteUseCase
)
