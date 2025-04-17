package com.androiddev.profilehub.utils

import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Nadya N. on 21.01.2025.
 */
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