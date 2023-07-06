package com.fappslab.bookshelf.main.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.libraries.arch.adapter.CommonDiffCallback

class MainAdapter(
    private val itemClicked: OnMainItemClicked
) : PagingDataAdapter<Book, MainHolder>(CommonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
        MainHolder.create(parent, itemClicked)

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}
