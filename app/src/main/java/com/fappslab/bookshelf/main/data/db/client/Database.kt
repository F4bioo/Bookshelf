package com.fappslab.bookshelf.main.data.db.client

interface Database<T> {
    fun create(): T
}
