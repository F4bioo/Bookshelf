package com.fappslab.bookshelf.main.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fappslab.bookshelf.main.data.model.local.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface BookshelfDao {

    @Query("SELECT * FROM book WHERE _id = :id")
    fun getBook(id: String): Maybe<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setBook(book: BookEntity): Completable

    @Query("SELECT * FROM book")
    fun getBooks(): Flowable<List<BookEntity>>

    @Query("SELECT * FROM book WHERE isFavorite = 1")
    fun getFavorites(): Flowable<List<BookEntity>>

    @Query("DELETE FROM book WHERE _id = :id")
    fun deleteBook(id: String): Completable
}
