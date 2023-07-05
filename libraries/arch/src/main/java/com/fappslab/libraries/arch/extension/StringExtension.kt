package com.fappslab.libraries.arch.extension

fun String?.orDash() = this ?: "-"

fun String?.httpsOrEmpty(): String = this?.replace(
    oldValue = "http://", newValue = "https://"
).orEmpty()

fun String.capitalize(): String =
    replaceFirstChar { it.uppercase() }
