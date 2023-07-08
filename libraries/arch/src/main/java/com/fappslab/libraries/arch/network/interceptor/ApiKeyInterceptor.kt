package com.fappslab.libraries.arch.network.interceptor

import com.fappslab.bookshelf.libraries.arch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val KEY = "key"

class ApiKeyInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url
        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(KEY, BuildConfig.API_KEY)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
