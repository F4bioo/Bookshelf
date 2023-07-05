package com.fappslab.libraries.arch.network.exception.extension

import com.fappslab.libraries.arch.network.exception.HttpThrowable
import com.fappslab.libraries.arch.network.exception.InternetThrowable
import com.fappslab.libraries.arch.network.exception.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

private const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error, please try again."

private fun HttpException.getCause(errorBody: String?): Throwable =
    getErrorResponse(errorBody).data?.let {
        HttpThrowable(code = it.code, message = it.message, throwable = this)
    } ?: HttpThrowable(message = UNEXPECTED_ERROR_MESSAGE, throwable = this)

private fun getErrorResponse(errorBody: String?): ErrorResponse =
    Gson().fromJson(errorBody, ErrorResponse::class.java)

private fun HttpException.parseError(): Throwable =
    getCause(errorBody = response()?.errorBody()?.string())

internal fun Throwable.toThrowable(): Throwable =
    when (this) {
        is HttpException -> parseError()
        is TimeoutException,
        is IOException -> InternetThrowable()

        else -> this
    }
