package com.fappslab.bookshelf.favorites.presentation.extension

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.parseAsHtml

fun String.extractYear(): String =
    split("-").firstOrNull() ?: "---"

fun TextView.concatString(@StringRes textRes: Int, text: String) {
    this.text = resources.getString(textRes, text).parseAsHtml()
}
