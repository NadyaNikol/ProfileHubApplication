package com.androiddev.profilehub.utils

import android.content.Context

/**
 * Created by Nadya N. on 14.06.2025.
 */

fun Int.dpToPx(context: Context): Float =
    this * context.resources.displayMetrics.density

fun Float.spToPx(context: Context): Float =
    this * context.resources.displayMetrics.density