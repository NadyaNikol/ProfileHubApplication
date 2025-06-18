package com.androiddev.profilehub.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getFloatOrThrow
import androidx.core.content.res.getStringOrThrow
import com.androiddev.profilehub.R
import com.androiddev.profilehub.ui.auth.GoogleSignInButton
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Nadya N. on 17.04.2025.
 */
fun View.customSnackbar(
    context: Context,
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
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

fun GoogleSignInButton.getStyledAttributes(context: Context, attrs: AttributeSet?): GoogleButtonStyle {

    val typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.GoogleSignInButton)

    val text = try {
        typedArray.getStringOrThrow(R.styleable.GoogleSignInButton_text)
    } catch (_: Exception) {
        Log.w(
            this::class.java.name,
            "parse: customText not found, using default: $DEFAULT_TEXT_GOOGLE_BUTTON"
        )
        DEFAULT_TEXT_GOOGLE_BUTTON
    }

    val textAllCaps = try {
        typedArray.getBooleanOrThrow(R.styleable.GoogleSignInButton_textAllCaps)

    } catch (_: Exception) {
        Log.w(
            this::class.java.name,
            "parse: customTextAllCaps not found, using default: $DEFAULT_TEXT_All_CAPS_GOOGLE_BUTTON"
        )
        DEFAULT_TEXT_All_CAPS_GOOGLE_BUTTON
    }

    val textSizePx = try {
        typedArray.getDimensionOrThrow(R.styleable.GoogleSignInButton_textSize)
    } catch (_: Exception) {
        val textSize = DEFAULT_TEXT_SIZE_GOOGLE_BUTTON.spToPx(context)
        Log.w(
            this::class.java.name,
            "parse: customTextSize not found, using default: $textSize px"
        )
        textSize
    }

    val letterSpacingEm = try {
        typedArray.getFloatOrThrow(R.styleable.GoogleSignInButton_letterSpacing)
    } catch (_: Exception) {
        val letterSpacingPx = DEFAULT_LETTER_SPACING_GOOGLE_BUTTON
        Log.w(
            this::class.java.name,
            "parse: customLetterSpacing not found, using default: $letterSpacingPx px"
        )
        letterSpacingPx
    }

    typedArray.recycle()

    val letterSpacingPx = letterSpacingEm * textSizePx
    return GoogleButtonStyle(
        text = if (textAllCaps) text.uppercase() else text,
        textSize = textSizePx,
        letterSpacing = letterSpacingPx)
}