package com.fappslab.bookshelf.favorites.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fappslab.bookshelf.R
import com.fappslab.bookshelf.databinding.ActivityFavoritesBinding
import com.fappslab.bookshelf.favorites.presentation.adapter.FavoritesAdapter
import com.fappslab.bookshelf.favorites.presentation.viewmodel.FavoritesViewAction
import com.fappslab.bookshelf.favorites.presentation.viewmodel.FavoritesViewModel
import com.fappslab.bookshelf.favorites.presentation.viewmodel.FavoritesViewState
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.presentation.extension.navigateToLinkIntent
import com.fappslab.bookshelf.main.presentation.extension.showErrorBuyBookAction
import com.fappslab.bookshelf.main.presentation.extension.showFeedbackDetails
import com.fappslab.libraries.arch.viewbinding.viewBinding
import com.fappslab.libraries.arch.viewmodel.onViewAction
import com.fappslab.libraries.arch.viewmodel.onViewState
import com.fappslab.libraries.design.extension.backPressedCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity(R.layout.activity_favorites) {

    private val binding: ActivityFavoritesBinding by viewBinding()
    private val viewModel: FavoritesViewModel by viewModel()
    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(viewModel::onAdapterItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecycler()
        setupObservables()
        setupListeners()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            state.showFeedbackDetailsState()
            flipperChildState(state.childPosition)
            submitListState(state.bookList)
        }

        onViewAction(viewModel) { action ->
            when (action) {
                is FavoritesViewAction.BuyBook -> navigateToExternalLinkAction(action.url)
                FavoritesViewAction.ShowErrorBuyBook -> showErrorBuyBookAction()
                FavoritesViewAction.BackPressed -> finish()
            }
        }
    }

    private fun setupListeners() = binding.run {
        includeEmpty.buttonBack.setOnClickListener { viewModel.onBackPressed() }
        backPressedCallback(block = viewModel::onBackPressed)
    }

    private fun setupRecycler() = binding.recyclerFavorites.run {
        adapter = favoritesAdapter
        itemAnimator = null
    }

    private fun FavoritesViewState.showFeedbackDetailsState() = book?.let {
        showFeedbackDetails(
            book = it,
            shouldShow = shouldShowDetails,
            favoriteAction = viewModel::onFavorite,
            dismissAction = viewModel::onDismissFeedbackDetails,
            buyAction = viewModel::onBuyBook
        )
    }

    private fun flipperChildState(childPosition: Int) {
        binding.flipperContainer.displayedChild = childPosition
    }

    private fun submitListState(bookList: List<Book>?) {
        favoritesAdapter.submitList(bookList)
    }

    private fun navigateToExternalLinkAction(url: String) {
        url.navigateToLinkIntent()
            .also(::startActivity)
    }

    companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, FavoritesActivity::class.java)
    }
}
