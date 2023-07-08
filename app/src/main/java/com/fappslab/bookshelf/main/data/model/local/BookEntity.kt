package com.fappslab.bookshelf.main.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    val id: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?,
    @ColumnInfo(name = "smallThumbnail")
    val smallThumbnail: String?,
    @ColumnInfo(name = "authors")
    val authors: List<String>?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "subtitle")
    val subtitle: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "infoLink")
    val infoLink: String?,
    @ColumnInfo(name = "publisher")
    val publisher: String,
    @ColumnInfo(name = "publishedDate")
    val publishedDate: String,
    @ColumnInfo(name = "pageCount")
    val pageCount: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean?
)
