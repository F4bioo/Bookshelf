package com.fappslab.bookshelf.favorites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fappslab.bookshelf.R
import com.fappslab.bookshelf.databinding.AdapterFavoritesItemBinding
import com.fappslab.bookshelf.favorites.presentation.extension.concatString
import com.fappslab.bookshelf.favorites.presentation.extension.extractYear
import com.fappslab.bookshelf.main.domain.model.Book

typealias OnFavoriteItemClicked = (id: Int, Book) -> Unit

class FavoritesHolder(
    private val binding: AdapterFavoritesItemBinding,
    private val itemClicked: OnFavoriteItemClicked
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Book) = binding.run {
        textTitle.text = item.title
        buttonFavorite.setOnClickListener {
            val book = item.copy(isFavorite = false)
            itemClicked(it.id, book)
        }
        cardItem.setOnClickListener {
            itemClicked(it.id, item)
        }
        textPublisher.concatString(
            R.string.publisher_placeholder,
            item.publisher
        )
        textPublishedYear.concatString(
            R.string.published_placeholder,
            item.publishedDate.extractYear()
        )
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: OnFavoriteItemClicked): FavoritesHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AdapterFavoritesItemBinding
                .inflate(inflater, parent, false)

            return FavoritesHolder(binding, onItemClicked)
        }
    }
}
