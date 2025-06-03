package com.androiddev.profilehub.utils

import android.view.View

/**
 * Created by Nadya N. on 17.04.2025.
 */

fun View.snackbarBuilder(
    message: String,
    duration: Int,
): SnackbarBuilder = SnackbarBuilder(this, message = message, duration = duration)
