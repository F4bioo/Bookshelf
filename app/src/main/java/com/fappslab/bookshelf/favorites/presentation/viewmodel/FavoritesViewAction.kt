package com.fappslab.bookshelf.favorites.presentation.viewmodel

sealed class FavoritesViewAction {
    data class BuyBook(val url: String) : FavoritesViewAction()
    object ShowErrorBuyBook : FavoritesViewAction()
    object BackPressed : FavoritesViewAction()
}
