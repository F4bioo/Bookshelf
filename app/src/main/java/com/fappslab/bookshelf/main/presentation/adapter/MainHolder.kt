package com.fappslab.bookshelf.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fappslab.bookshelf.databinding.AdapterMainItemBinding
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.libraries.design.extension.loadImage

typealias OnMainItemClicked = (Book) -> Unit

class MainHolder(
    private val binding: AdapterMainItemBinding,
    private val itemClicked: OnMainItemClicked
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Book) = binding.run {
        textTitle.text = item.title
        imageCover.loadImage(item.thumbnail)
        cardItem.setOnClickListener { itemClicked(item) }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: OnMainItemClicked): MainHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AdapterMainItemBinding
                .inflate(inflater, parent, false)

            return MainHolder(binding, onItemClicked)
        }
    }
}
