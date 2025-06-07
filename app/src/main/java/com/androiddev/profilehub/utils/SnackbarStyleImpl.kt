package com.androiddev.profilehub.utils

import android.view.Gravity
import androidx.core.content.ContextCompat
import com.androiddev.profilehub.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Nadya N. on 23.05.2025.
 */
data class SnackbarStyleImpl(
    val backgroundColor: Int = R.color.colorSecondary,
    val textColor: Int = R.color.colorOnSecondary,
    val actionTextColor: Int = R.color.colorOnSecondary,
    val viewBackground: Int = R.drawable.snack_bar_shape_round,
) : SnackbarStyle {
    override fun apply(snackbar: Snackbar): Snackbar {

        snackbar.apply {
            setBackgroundTint(ContextCompat.getColor(context, backgroundColor))
            setTextColor(ContextCompat.getColor(context, textColor))
            setActionTextColor(ContextCompat.getColor(context, actionTextColor))
            view.setBackgroundResource(viewBackground)

            setGravity(Gravity.CENTER or Gravity.TOP)
            setMargins(
                left = 16,
                top = 24,
                right = 16,
                bottom = 0
            )
        }

        return snackbar
    }
}
