package com.fappslab.bookshelf.main.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fappslab.bookshelf.main.data.model.local.BookEntity
import com.fappslab.bookshelf.main.data.model.local.Converters

@Database(entities = [BookEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class BookshelfDatabase : RoomDatabase() {
    abstract fun bookshelfDao(): BookshelfDao
}
