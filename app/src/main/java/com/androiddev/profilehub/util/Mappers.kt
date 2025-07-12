package com.androiddev.profilehub.util

import android.content.Context
import android.util.TypedValue

/**
 * Created by Nadya N. on 14.06.2025.
 */

fun Context.dpToPx(dp: Float): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )

fun Context.spToPx(sp: Float): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        this.resources.displayMetrics
    )