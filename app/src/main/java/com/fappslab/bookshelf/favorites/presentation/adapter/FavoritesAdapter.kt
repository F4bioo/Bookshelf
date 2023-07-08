package com.fappslab.bookshelf.favorites.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.presentation.adapter.OnMainItemClicked
import com.fappslab.libraries.arch.adapter.CommonDiffCallback

class FavoritesAdapter(
    private val itemClicked: OnFavoriteItemClicked
) : ListAdapter<Book, FavoritesHolder>(CommonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesHolder =
        FavoritesHolder.create(parent, itemClicked)

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}
