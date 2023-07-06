package com.fappslab.libraries.design.extension

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import coil.load
import coil.request.CachePolicy

fun ImageView.loadImage(urlImage: String) {
    load(urlImage) {
        crossfade(enable = true)
        crossfade(durationMillis = 500)
        memoryCachePolicy(CachePolicy.ENABLED)
    }
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    setColorFilter(ContextCompat.getColor(context, colorRes))
}

fun Context.getColorRes(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.isDarkModeActivated(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun View.hideKeyboard() {
    val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    im?.hideSoftInputFromWindow(windowToken, 0)
}

fun SearchView.setOnQueryTextListener(submitAction: (query: String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean = false
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let(submitAction::invoke)
            return true
        }
    })
}

fun FragmentActivity.backPressedCallback(isEnable: Boolean = true, block: () -> Unit) {
    val callback = object : OnBackPressedCallback(isEnable) {
        override fun handleOnBackPressed() {
            block()
        }
    }
    onBackPressedDispatcher.addCallback(this, callback)
}
