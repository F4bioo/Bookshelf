package com.fappslab.libraries.design.dsmodal

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.fappslab.libraries.arch.extension.isNotNull

@Suppress("unused")
fun LifecycleOwner.dsFeedbackModal(
    block: DsFeedbackModal.() -> Unit
): DsFeedbackModal = DsFeedbackModal().apply(block)

fun DsFeedbackModal.build(
    shouldShow: Boolean = true,
    manager: FragmentManager,
    tag: String
) {
    manager.hide(tag)
    if (shouldShow) {
        show(manager, tag)
    }
}

private fun FragmentManager.hide(tag: String) {
    if (isShowing(tag)) {
        val dialog = findFragmentByTag(tag)
        if (dialog is DsFeedbackModal) dialog.dismissAllowingStateLoss()
    }
}

private fun FragmentManager.isShowing(tag: String): Boolean {
    executePendingTransactions()
    return findFragmentByTag(tag).isNotNull()
}
