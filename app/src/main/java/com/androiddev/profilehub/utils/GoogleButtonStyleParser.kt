package com.androiddev.profilehub.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getFloatOrThrow
import com.androiddev.profilehub.R

/**
 * Created by Nadya N. on 14.06.2025.
 */
object GoogleButtonStyleParser {
    fun parse(context: Context, attrs: AttributeSet?, defStyleAttr: Int): GoogleButtonStyle {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.GoogleSignInButton, defStyleAttr, 0)
        return try {
            val text =
                typedArray.getString(R.styleable.GoogleSignInButton_customText) ?: run {
                    Log.w(
                        this::class.java.name,
                        "parse: customText not found, using default: ${DEFAULT_TEXT_GOOGLE_BUTTON}"
                    )
                    DEFAULT_TEXT_GOOGLE_BUTTON
                }

            val textAllCaps = try {
                typedArray.getBooleanOrThrow(R.styleable.GoogleSignInButton_customTextAllCaps)

            } catch (_: Exception) {
                Log.w(
                    this::class.java.name,
                    "parse: customTextAllCaps not found, using default: ${DEFAULT_TEXT_All_CAPS_GOOGLE_BUTTON}"
                )
                DEFAULT_TEXT_All_CAPS_GOOGLE_BUTTON
            }

            val textSize = try {
                typedArray.getDimensionOrThrow(R.styleable.GoogleSignInButton_customTextSize)
            } catch (_: Exception) {
                val textSizeSp = DEFAULT_TEXT_SIZE_GOOGLE_BUTTON.spToPx(context)
                Log.w(
                    this::class.java.name,
                    "parse: customTextSize not found, using default: $textSizeSp sp"
                )
                textSizeSp
            }

            val letterSpacingEm = try {
                typedArray.getFloatOrThrow(R.styleable.GoogleSignInButton_customLetterSpacing)
            } catch (_: Exception) {
                val letterSpacingPx = DEFAULT_LETTER_SPACING_GOOGLE_BUTTON
                Log.w(
                    this::class.java.name,
                    "parse: customLetterSpacing not found, using default: $letterSpacingPx px"
                )
                letterSpacingPx
            }

            val letterSpacingPx = letterSpacingEm * textSize

            GoogleButtonStyle(text, textSize, letterSpacingPx, textAllCaps)
        } finally {
            typedArray.recycle()
        }
    }
}
