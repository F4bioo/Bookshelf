package com.fappslab.libraries.arch.network.exception

data class HttpThrowable(
    val code: Int? = null,
    override val message: String? = null,
    private val throwable: Throwable? = null
) : Throwable(message, throwable)
