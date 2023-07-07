package com.fappslab.bookshelf.main.presentation.extension

import android.content.Intent
import android.net.Uri
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import coil.load
import coil.transform.RoundedCornersTransformation
import com.fappslab.bookshelf.R
import com.fappslab.bookshelf.databinding.DialogDetailsBinding
import com.fappslab.bookshelf.main.domain.model.Book
import com.fappslab.bookshelf.main.presentation.model.ChipType
import com.fappslab.libraries.design.dsmodal.build
import com.fappslab.libraries.design.dsmodal.dsFeedbackModal
import com.google.android.material.chip.Chip
import com.fappslab.bookshelf.libraries.design.R as DS
import com.google.android.material.R as AS


private const val FEEDBACK_ERROR_TAG = "FEEDBACK_ERROR_TAG"
private const val FEEDBACK_DETAILS_TAG = "FEEDBACK_DETAILS_TAG"
private const val BUY_QUERY = "&sitesec=buy"

fun FragmentActivity.showFeedbackError(
    shouldShow: Boolean, message: String, primaryAction: () -> Unit, dismissAction: () -> Unit
) {
    dsFeedbackModal {
        titleRes = DS.string.common_error_title
        messageText = message
        primaryButton = {
            buttonTextRes = DS.string.common_try_again
            buttonAction = primaryAction
        }
        secondaryButton = {
            buttonTextRes = DS.string.common_cancel
            buttonAction = dismissAction
        }
        closeButton = dismissAction
        shouldBlock = true
    }.build(shouldShow, supportFragmentManager, FEEDBACK_ERROR_TAG)
}

fun FragmentActivity.showFeedbackDetails(
    shouldShow: Boolean,
    book: Book?,
    favoriteAction: (Book) -> Unit,
    buyAction: (Pair<Boolean, String>) -> Unit,
    dismissAction: () -> Unit
) {

    book?.run {
        val binding = DialogDetailsBinding.inflate(layoutInflater).apply {
            textTitle.text = title
            textSubtitle.text = subtitle
            textAuthor.text = authors.toString()
            textDescription.text = description
            textPublisher.text = publisher
            textPublishedDate.text = publishedDate
            textPageCount.text = pageCount
            checkFavorite.isChecked = isFavorite
            buttonClose.setOnClickListener { dismissAction() }
            imageCover.load(book.thumbnail) {
                transformations(RoundedCornersTransformation(radius = 28f))
            }
            checkFavorite.setOnCheckedChangeListener { _, isChecked ->
                favoriteAction(book.copy(isFavorite = isChecked))
            }
            buttonBuy.setOnClickListener {
                buyAction(book.infoLink.urlSanitize())
            }
        }

        dsFeedbackModal {
            customView = binding.root
            shouldBlock = true
        }.build(shouldShow, supportFragmentManager, FEEDBACK_DETAILS_TAG)
    }
}

fun AppCompatActivity.showErrorBuyBookAction() {
    Toast.makeText(this, getString(R.string.error_buy_book), Toast.LENGTH_LONG).show()
}

fun FragmentActivity.createChip(chipType: ChipType, primaryAction: (query: String) -> Unit): Chip =
    Chip(ContextThemeWrapper(this, AS.style.Widget_MaterialComponents_Chip_Action)).apply {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        setOnClickListener { primaryAction(chipType.name) }
        id = View.generateViewId()
        setText(chipType.textRes)
        isCloseIconVisible = false
        isCheckedIconVisible = false
        isChipIconVisible = false
        isCheckable = false
        isClickable = true
        isFocusable = true
    }

fun String.navigateToLinkIntent(): Intent {
    val url = this@navigateToLinkIntent
    return Intent(Intent.ACTION_VIEW)
        .apply { data = Uri.parse(url) }
}

fun String.urlSanitize(): Pair<Boolean, String> {
    val url = "$this$BUY_QUERY"
    return URLUtil.isValidUrl(url) to url
}
