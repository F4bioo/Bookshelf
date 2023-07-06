package com.fappslab.bookshelf.main.di

import com.fappslab.bookshelf.libraries.arch.BuildConfig
import com.fappslab.bookshelf.main.data.api.BookshelfService
import com.fappslab.bookshelf.main.data.db.BookshelfDatabase
import com.fappslab.bookshelf.main.data.db.client.Database
import com.fappslab.bookshelf.main.data.db.client.DatabaseImpl
import com.fappslab.bookshelf.main.data.db.client.RoomDatabaseBuilder
import com.fappslab.bookshelf.main.data.repository.BookshelfRepositoryImpl
import com.fappslab.bookshelf.main.data.source.local.BookshelfLocalDataSourceImpl
import com.fappslab.bookshelf.main.data.source.remote.BookshelfRemoteDataSourceImpl
import com.fappslab.bookshelf.main.domain.repository.BookshelfRepository
import com.fappslab.bookshelf.main.domain.usecase.GetBooksUseCase
import com.fappslab.bookshelf.main.domain.usecase.GetFavoriteUseCase
import com.fappslab.bookshelf.main.domain.usecase.SetFavoriteUseCase
import com.fappslab.bookshelf.main.presentation.viewmodel.MainViewModel
import com.fappslab.libraries.arch.koinload.KoinLoad
import com.fappslab.libraries.arch.network.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

object MainModule : KoinLoad() {

    override val dataModule = module {
        single<RoomDatabaseBuilder<BookshelfDatabase>> {
            RoomDatabaseBuilder(
                context = get(),
                databaseName = BuildConfig.DB_NAME,
                databaseClass = BookshelfDatabase::class.java
            )
        }

        single<Database<BookshelfDatabase>> {
            DatabaseImpl(
                database = get<RoomDatabaseBuilder<BookshelfDatabase>>().build()
            )
        }
    }

    override val presentationModule: Module = module {
        viewModel {
            val repository = getBookshelfRepository()
            MainViewModel(
                getBooksUseCase = GetBooksUseCase(repository),
                getFavoriteUseCase = GetFavoriteUseCase(repository),
                setFavoriteUseCase = SetFavoriteUseCase(repository)
            )
        }
    }
}

fun Scope.getBookshelfRepository(): BookshelfRepository =
    BookshelfRepositoryImpl(
        remoteDataSource = BookshelfRemoteDataSourceImpl(
            service = get<HttpClient>().create(
                clazz = BookshelfService::class.java
            )
        ),
        localDataSource = BookshelfLocalDataSourceImpl(
            dao = get<Database<BookshelfDatabase>>().create()
                .bookshelfDao()
        )
    )
