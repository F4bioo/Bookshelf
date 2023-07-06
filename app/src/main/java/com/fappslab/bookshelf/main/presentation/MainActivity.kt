package com.fappslab.bookshelf.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.fappslab.bookshelf.R
import com.fappslab.bookshelf.databinding.ActivityMainBinding
import com.fappslab.bookshelf.favorites.presentation.FavoritesActivity
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.presentation.adapter.LoadAdapter
import com.fappslab.bookshelf.main.presentation.adapter.MainAdapter
import com.fappslab.bookshelf.main.presentation.extension.createChip
import com.fappslab.bookshelf.main.presentation.extension.showFeedbackDetails
import com.fappslab.bookshelf.main.presentation.extension.showFeedbackError
import com.fappslab.bookshelf.main.presentation.model.ChipType
import com.fappslab.bookshelf.main.presentation.viewmodel.MainViewAction
import com.fappslab.bookshelf.main.presentation.viewmodel.MainViewModel
import com.fappslab.bookshelf.main.presentation.viewmodel.MainViewState
import com.fappslab.libraries.arch.viewbinding.viewBinding
import com.fappslab.libraries.arch.viewmodel.onViewAction
import com.fappslab.libraries.arch.viewmodel.onViewState
import com.fappslab.libraries.design.extension.backPressedCallback
import com.fappslab.libraries.design.extension.setOnQueryTextListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.fappslab.bookshelf.libraries.design.R as DS

private const val SPAN_COUNT = 2

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(viewModel::onAdapterItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecycler()
        setupObservables()
        setupListeners()
        setupChips()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            progressSearchState(state.shouldShowLoading)
            flipperChildState(state.childPosition)
            submitDataState(state.pagingData)
            state.showFeedbackDetailsState()
            state.showFeedbackErrorState()
        }

        onViewAction(viewModel) { action ->
            when (action) {
                MainViewAction.Favorites -> navigateToFavoritesAction()
                MainViewAction.HiddenKeyboard -> hideKeyboardAction()
                MainViewAction.TryAgain -> tryAgainAction()
                MainViewAction.BackPressed -> finish()
            }
        }
    }

    private fun setupListeners() = binding.run {
        includeChip.buttonFavorites.setOnClickListener { viewModel.onFavoritesButton() }
        includeSearch.searchCover.setOnQueryTextListener(viewModel::onSearch)
        mainAdapter.addLoadStateListener(viewModel::onCombinedLoadState)
        fab.setOnClickListener { viewModel.onFavoritesButton() }
        backPressedCallback(block = viewModel::onBackPressed)
    }

    private fun setupRecycler() = binding.recyclerMain.run {
        adapter = mainAdapter.withLoadStateFooter(LoadAdapter(mainAdapter::retry))
        layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, VERTICAL)
        itemAnimator = null
    }

    private fun submitDataState(pagingData: PagingData<Book>?) {
        pagingData?.let { mainAdapter.submitData(lifecycle, pagingData = it) }
    }

    private fun flipperChildState(childPosition: Int) {
        binding.flipperContainer.displayedChild = childPosition
    }

    private fun progressSearchState(isVisible: Boolean) {
        binding.progress.isVisible = isVisible
    }

    private fun MainViewState.showFeedbackDetailsState() {
        showFeedbackDetails(
            book = book,
            shouldShow = shouldShowDetails,
            favoriteAction = viewModel::onFavorite,
            dismissAction = viewModel::onDismissFeedbackDetails
        )
    }

    private fun MainViewState.showFeedbackErrorState() {
        showFeedbackError(
            shouldShow = shouldShowError,
            message = errorMessage ?: getString(DS.string.common_error_message),
            primaryAction = viewModel::onTryAgain,
            dismissAction = viewModel::onDismissFeedbackError
        )
    }

    private fun navigateToFavoritesAction() {
        FavoritesActivity.createIntent(context = this)
            .also(::startActivity)
    }

    private fun hideKeyboardAction() {
        binding.includeSearch.searchCover.clearFocus()
    }

    private fun tryAgainAction() {
        hideKeyboardAction()
        mainAdapter.retry()
    }

    private fun setupChips() = binding.includeChip.run {
        ChipType.list.forEach { chipType ->
            val chip = createChip(chipType, viewModel::getBooks)
            chipGroup.addView(chip)
        }
    }
}
