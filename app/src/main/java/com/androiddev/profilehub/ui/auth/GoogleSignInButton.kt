package com.androiddev.profilehub.ui.auth

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColorInt
import com.androiddev.profilehub.R
import com.androiddev.profilehub.utils.MIN_WIDTH_GOOGLE_BUTTON
import kotlin.math.max

/**
 * Created by Nadya N. on 10.06.2025.
 */

class GoogleSignInButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    val googleIconBitmap = ContextCompat.getDrawable(context, R.drawable.google_icon)?.toBitmap()

    private val bgColor = ContextCompat.getColor(context, R.color.colorSecondary)
    private val cornerRadius = context.resources.getDimension(R.dimen.btn_corner_radius)
    private val minHeightPx = context.resources.getDimensionPixelSize(R.dimen.btn_narrow_min_height)
    private val text = context.getString(R.string.btn_google).uppercase()
    private val typefaceGoogle = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold)
    private val textSizeGoogleSp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics
    )
    private val letterSpacingGooglePx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        1.5f,
        context.resources.displayMetrics
    )

    private val googleColors = listOf(
        "#4285F4".toColorInt(), // Blue
        "#EA4335".toColorInt(), // Red
        "#FBBC05".toColorInt(), // Yellow
        "#4285F4".toColorInt(), // Blue
        "#34A853".toColorInt(), // Green
        "#EA4335".toColorInt()  // Red
    )

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = bgColor
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = textSizeGoogleSp
        typeface = typefaceGoogle
    }

    init {
        isClickable = true
        contentDescription = context.getString(R.string.desc_google)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = max(minHeightPx, suggestedMinimumHeight)
        val width = resolveSize(
            MIN_WIDTH_GOOGLE_BUTTON.dpToPx(context).toInt(),
            widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun Int.dpToPx(context: Context): Float =
        this * context.resources.displayMetrics.density

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, backgroundPaint)

        val iconSize = height * 0.5f
        val spacing = 24f

        val textWidth = textPaint.measureText(text)

        val centerY = height / 2f
        val textBaseline = centerY - (textPaint.descent() + textPaint.ascent()) / 2

        var textStartX = (width - textWidth) / 2f
        val iconLeft = textStartX - spacing - iconSize
        val iconTop = centerY - iconSize / 2f

        googleIconBitmap?.let {
            canvas.drawBitmap(
                it,
                null,
                RectF(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize),
                null
            )
        }

        for ((index, char) in text.withIndex()) {
            textPaint.color = googleColors[index % googleColors.size]
            val charStr = char.toString()

            canvas.drawText(
                charStr,
                textStartX,
                textBaseline,
                textPaint
            )
            textStartX += textPaint.measureText(charStr) + letterSpacingGooglePx
        }
    }
}