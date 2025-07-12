package com.androiddev.profilehub.ui.auth

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getFloatOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.graphics.drawable.toBitmap
import com.androiddev.profilehub.R
import com.androiddev.profilehub.util.GoogleButtonStyle
import com.androiddev.profilehub.util.dpToPx
import com.androiddev.profilehub.util.spToPx
import kotlin.math.max
import kotlin.properties.Delegates

/**
 * Created by Nadya N. on 10.06.2025.
 */

class GoogleSignInButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    val googleIconBitmap = ContextCompat
        .getDrawable(context, R.drawable.google_icon)
        ?.toBitmap()

    private val bgColor = ContextCompat.getColor(context, R.color.colorSecondary)
    private val textColor = ContextCompat.getColor(context, R.color.colorOnSecondary)
    private val cornerRadius = context.resources.getDimension(R.dimen.btn_corner_radius)
    private val minHeightPx = context.resources.getDimensionPixelSize(R.dimen.btn_narrow_min_height)
    private val typefaceGoogle = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold)

    private lateinit var text: String
    private var textSizeGooglePx by Delegates.notNull<Float>()
    private var letterSpacingGooglePx by Delegates.notNull<Float>()
    private val textPaint: Paint

    private val rectRound = RectF()
    private val rectBitmap = RectF()

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = bgColor
        style = Paint.Style.FILL
    }

    init {
        applyStyleFromAttrs(attrs)

        isClickable = true
        contentDescription = context.getString(R.string.desc_google)

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = textSizeGooglePx
            typeface = typefaceGoogle
            color = textColor
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBackground(canvas)

        val centerY = height / 2f
        val textStartX = (width - textPaint.measureText(text)) / 2f

        drawGoogleIcon(canvas, textStartX, centerY)
        drawText(canvas, textStartX, centerY)
    }

    private fun applyStyleFromAttrs(
        attrs: AttributeSet?,
    ) {
        val style = getStyledAttributes(context, attrs)
        text = style.text
        textSizeGooglePx = style.textSize
        letterSpacingGooglePx = style.letterSpacing

        invalidate()
    }

    private fun measureWidth(widthSpec: Int): Int {
        val minWidth = context.dpToPx(MIN_WIDTH_GOOGLE_BUTTON).toInt()
        val mode = MeasureSpec.getMode(widthSpec)
        val size = MeasureSpec.getSize(widthSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> minWidth.coerceAtMost(size)
            MeasureSpec.UNSPECIFIED -> minWidth
            else -> minWidth
        }
    }

    private fun measureHeight(heightSpec: Int): Int {
        val minHeight = max(minHeightPx, suggestedMinimumHeight)
        val mode = MeasureSpec.getMode(heightSpec)
        val size = MeasureSpec.getSize(heightSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> minHeight.coerceAtMost(size)
            MeasureSpec.UNSPECIFIED -> minHeight
            else -> minHeight
        }
    }

    private fun drawBackground(canvas: Canvas) {
        rectRound.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rectRound, cornerRadius, cornerRadius, backgroundPaint)
    }

    private fun drawGoogleIcon(canvas: Canvas, textStartX: Float, centerY: Float) {
        val iconSize = height * 0.5f
        val iconLeft = textStartX - DEFAULT_SPACING_IMAGE_GOOGLE_BUTTON - iconSize
        val iconTop = centerY - iconSize / 2f
        rectBitmap.set(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize)

        googleIconBitmap?.let {
            canvas.drawBitmap(it, null, rectBitmap, null)
        }
    }

    private fun drawText(canvas: Canvas, startX: Float, centerY: Float) {
        val textBaseline = centerY - (textPaint.descent() + textPaint.ascent()) / 2
        var x = startX

        text.forEach { char ->
            val charStr = char.toString()
            canvas.drawText(charStr, x, textBaseline, textPaint)
            x += textPaint.measureText(charStr) + letterSpacingGooglePx
        }
    }

    private fun getStyledAttributes(context: Context, attrs: AttributeSet?): GoogleButtonStyle {

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
            val textSize = context.spToPx(DEFAULT_TEXT_SIZE_GOOGLE_BUTTON)
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
            letterSpacing = letterSpacingPx
        )
    }

    companion object {
        const val MIN_WIDTH_GOOGLE_BUTTON = 328F
        const val DEFAULT_TEXT_SIZE_GOOGLE_BUTTON = 16f
        const val DEFAULT_LETTER_SPACING_GOOGLE_BUTTON = 1.5f
        const val DEFAULT_TEXT_GOOGLE_BUTTON = "Google"
        const val DEFAULT_TEXT_All_CAPS_GOOGLE_BUTTON = true
        const val DEFAULT_SPACING_IMAGE_GOOGLE_BUTTON = 16f
    }

}