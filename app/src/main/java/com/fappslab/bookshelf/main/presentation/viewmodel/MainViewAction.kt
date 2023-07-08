package com.fappslab.bookshelf.main.presentation.viewmodel

sealed class MainViewAction {
    data class BuyBook(val url: String) : MainViewAction()
    object ShowErrorBuyBook : MainViewAction()
    object BackPressed : MainViewAction()
    object Favorites : MainViewAction()
    object HiddenKeyboard : MainViewAction()
    object TryAgain : MainViewAction()
}
