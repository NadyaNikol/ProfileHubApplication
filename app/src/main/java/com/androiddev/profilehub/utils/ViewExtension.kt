package com.androiddev.profilehub.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import com.androiddev.profilehub.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Nadya N. on 17.04.2025.
 */
fun View.customSnackbar(
    context: Context,
    message: String,
    duration: Int = Snackbar.LENGTH_LONG
): Snackbar {
    val snackbar = Snackbar.make(this, message, duration)

    snackbar.setGravity(Gravity.CENTER)
        .setMargins(
            context.resources.getDimensionPixelOffset(R.dimen.margin_start),
            0,
            context.resources.getDimensionPixelOffset(R.dimen.margin_end),
            context.resources.getDimensionPixelOffset(R.dimen.margin_bottom)
        )
        .setBackgroundTint(
            ContextCompat.getColor(context, R.color.colorSecondary)
        )
        .setTextColor(
            ContextCompat.getColor(context, R.color.colorOnSecondary)
        )
        .apply {
            view.background = ContextCompat.getDrawable(
                context,
                R.drawable.snack_bar_shape_round
            )
        }
    return snackbar
}