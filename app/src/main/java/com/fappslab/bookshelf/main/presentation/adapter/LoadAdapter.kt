package com.fappslab.bookshelf.main.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoadAdapter(
    private val errorClicked: OnErrorClicked
) : LoadStateAdapter<LoadHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadHolder =
        LoadHolder.create(parent, errorClicked)

    override fun onBindViewHolder(holder: LoadHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
