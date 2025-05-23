package com.androiddev.profilehub.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Nadya N. on 23.05.2025.
 */
class SnackbarBuilder(
    private val parentView: View,
    private val message: String,
    private val duration: Int = Snackbar.LENGTH_LONG,
) {
    private var snackbar = Snackbar.make(parentView, message, duration)

    fun setStyle(newStyle: SnackbarStyle = SnackbarStyleImpl()): SnackbarBuilder {
        snackbar = newStyle.apply(snackbar)
        return this
    }

    fun setAction(text: String, action: () -> Unit) = apply {
        text.let { text ->
            snackbar.setAction(text) { action.invoke() }
        }
    }

    fun setCallback(callback: Snackbar.Callback) = apply {
        snackbar.addCallback(callback)
    }

    fun show() {
        snackbar.show()
    }
}
