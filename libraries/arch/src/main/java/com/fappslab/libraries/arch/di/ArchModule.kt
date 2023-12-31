package com.fappslab.libraries.arch.di

import com.fappslab.bookshelf.libraries.arch.BuildConfig
import com.fappslab.libraries.arch.koinload.KoinLoad
import com.fappslab.libraries.arch.koinload.KoinQualifier
import com.fappslab.libraries.arch.network.client.HttpClient
import com.fappslab.libraries.arch.network.client.HttpClientImpl
import com.fappslab.libraries.arch.network.retrofit.RetrofitClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object RetrofitInterceptorQualifier : KoinQualifier

object ArchModule : KoinLoad() {

    override val dataModule: Module = module {
        single<Retrofit> {
            RetrofitClient(
                baseUrl = BuildConfig.BASE_URL,
                interceptors = get(qualifier = RetrofitInterceptorQualifier)
            ).create()
        }

        single<HttpClient> { HttpClientImpl(retrofit = get()) }
    }
}
