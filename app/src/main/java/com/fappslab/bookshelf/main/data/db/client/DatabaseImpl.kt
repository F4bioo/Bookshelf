package com.fappslab.bookshelf.main.data.db.client

import com.fappslab.bookshelf.main.data.db.BookshelfDatabase

class DatabaseImpl(
    private val database: BookshelfDatabase
) : Database<BookshelfDatabase> {

    override fun create(): BookshelfDatabase {
        return database
    }
}
