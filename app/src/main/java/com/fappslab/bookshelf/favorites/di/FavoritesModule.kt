package com.fappslab.bookshelf.favorites.di

import com.fappslab.bookshelf.favorites.domain.usecase.FavoritesUseCaseProvider
import com.fappslab.bookshelf.favorites.presentation.viewmodel.FavoritesViewModel
import com.fappslab.bookshelf.main.di.getBookshelfRepository
import com.fappslab.bookshelf.main.domain.usecase.GetFavoritesUseCase
import com.fappslab.bookshelf.main.domain.usecase.SetFavoriteUseCase
import com.fappslab.libraries.arch.koinload.KoinLoad
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object FavoritesModule : KoinLoad() {

    override val presentationModule: Module = module {
        viewModel {
            val repository = getBookshelfRepository()
            FavoritesViewModel(
                provider = FavoritesUseCaseProvider(
                    getFavoritesUseCase = GetFavoritesUseCase(repository),
                    setFavoriteUseCase = SetFavoriteUseCase(repository)
                )
            )
        }
    }
}
