package com.androiddev.profilehub.utils

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import com.androiddev.profilehub.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Nadya N. on 19.05.2025.
 */

fun EditText.updateIfDifferent(newText: String) {
    if (text.toString() != newText) {
        setText(newText)
    }
}

fun <T> EditText.setAfterTextChangedListener(
    event: (String) -> T,
    onEvent: (T) -> Unit,
) {
    this.doAfterTextChanged { editable ->
        val ev = event(editable.toString())
        onEvent(ev)
    }
}

fun CheckBox.updateIfDifferent(newChecked: Boolean) {
    if (isChecked != newChecked) {
        isChecked = newChecked
    }
}

fun ImageView.loadImage(view: View, url: String) {
    Glide.with(view)
        .load(url)
        .error(R.drawable.person_icon)
        .into(this)
}

fun View.snackbarBuilder(
    message: String,
    duration: Int,
): SnackbarBuilder = SnackbarBuilder(this, message = message, duration = duration)

fun Snackbar.setGravity(gravity: Int): Snackbar {
    val snackBarView = this.view
    val params = snackBarView.layoutParams as FrameLayout.LayoutParams
    params.gravity = gravity
    snackBarView.layoutParams = params
    return this
}

fun Snackbar.setMargins(left: Int, top: Int, right: Int, bottom: Int): Snackbar {
    val snackBarView = this.view
    val params = snackBarView.layoutParams as FrameLayout.LayoutParams
    params.setMargins(left, top, right, bottom)
    snackBarView.layoutParams = params
    return this
}

