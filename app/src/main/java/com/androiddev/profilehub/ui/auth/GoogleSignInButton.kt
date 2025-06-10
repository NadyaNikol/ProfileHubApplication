package com.androiddev.profilehub.ui.auth

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.util.TypedValueCompat.dpToPx
import com.androiddev.profilehub.R
import com.google.android.material.button.MaterialButton

/**
 * Created by Nadya N. on 10.06.2025.
 */

class GoogleSignInButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.materialButtonStyle,
) : MaterialButton(context, attrs, defStyleAttr) {

    init {
        setupGoogleStyle()
    }

    private fun setupGoogleStyle() {
        val bgColor = ContextCompat.getColor(context, R.color.colorSecondary)
        backgroundTintList = ColorStateList.valueOf(bgColor)

        icon = ContextCompat.getDrawable(context, R.drawable.google_icon)
        iconGravity = ICON_GRAVITY_TEXT_START
        iconTint = null

        setTextColor(ContextCompat.getColor(context, R.color.colorOnSecondary))
        setText(R.string.btn_google)

        contentDescription = ContextCompat.getString(context, R.string.desc_google)

        letterSpacing = 0.15f

        minHeight =
            resources.getDimensionPixelSize(R.dimen.btn_narrow_min_height)
        setPaddingRelative(
            paddingLeft,
            dpToPx(8f, resources.displayMetrics).toInt(),
            paddingRight,
            dpToPx(8f, resources.displayMetrics).toInt()
        )

        cornerRadius = resources.getDimensionPixelSize(R.dimen.btn_corner_radius)
    }
}