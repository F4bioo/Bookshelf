package com.fappslab.libraries.arch.network.exception.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val data: DataResponse?
) {
    data class DataResponse(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("message")
        val message: String?
    )
}
