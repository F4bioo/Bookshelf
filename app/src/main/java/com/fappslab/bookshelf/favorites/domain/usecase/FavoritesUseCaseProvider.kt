package com.fappslab.bookshelf.favorites.domain.usecase

import com.fappslab.bookshelf.main.domain.usecase.GetFavoritesUseCase
import com.fappslab.bookshelf.main.domain.usecase.SetFavoriteUseCase

data class FavoritesUseCaseProvider(
    val getFavoritesUseCase: GetFavoritesUseCase,
    val setFavoriteUseCase: SetFavoriteUseCase
)
