package com.fappslab.bookshelf.main.domain.model

data class Book(
    val id: String,
    val thumbnail: String,
    val smallThumbnail: String,
    val authors: List<String>,
    val title: String,
    val subtitle: String,
    val description: String,
    val infoLink: String,
    val publisher: String,
    val publishedDate: String,
    val pageCount: String,
    val isFavorite: Boolean = false
)
