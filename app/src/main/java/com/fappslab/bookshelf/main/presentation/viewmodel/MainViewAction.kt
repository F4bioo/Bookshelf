package com.fappslab.bookshelf.main.presentation.viewmodel

sealed class MainViewAction {
    object BackPressed : MainViewAction()
    object Favorites : MainViewAction()
    object HiddenKeyboard : MainViewAction()
    object TryAgain : MainViewAction()
}
