package com.fappslab.bookshelf.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fappslab.bookshelf.databinding.LoadItemBinding

private const val CHILD_EMPTY = 0
private const val CHILD_LOADING = 1
private const val CHILD_FAILURE = 2

typealias OnErrorClicked = () -> Unit

class LoadHolder(
    private val binding: LoadItemBinding,
    private val errorClicked: OnErrorClicked
) : RecyclerView.ViewHolder(binding.root) {

    init {
        setupListeners()
        setupFooter()
    }

    fun bind(loadState: LoadState) = binding.run {
        flipperContainer.displayedChild = when (loadState) {
            is LoadState.Loading -> CHILD_LOADING
            is LoadState.Error -> CHILD_FAILURE
            else -> CHILD_EMPTY
        }
    }

    private fun setupListeners() = binding.run {
        buttonTryAgain.setOnClickListener { errorClicked() }

    }

    private fun setupFooter() = binding.run {
        val layoutParams = flipperContainer.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = true
        }
    }

    companion object {
        fun create(parent: ViewGroup, errorClicked: OnErrorClicked): LoadHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadItemBinding
                .inflate(inflater, parent, false)

            return LoadHolder(binding, errorClicked)
        }
    }
}
