package com.fappslab.bookshelf.main.data.db.client

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal class RoomDatabaseBuilder<T : RoomDatabase>(
    private val context: Context,
    private val databaseName: String,
    private val databaseClass: Class<T>
) {

    fun build(): T {
        return Room.databaseBuilder(
            context,
            databaseClass,
            databaseName
        ).build()
    }
}
