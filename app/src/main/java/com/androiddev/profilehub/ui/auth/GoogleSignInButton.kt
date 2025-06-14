package com.androiddev.profilehub.ui.auth

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.androiddev.profilehub.R
import com.androiddev.profilehub.utils.DEFAULT_SPACING_IMAGE_GOOGLE_BUTTON
import com.androiddev.profilehub.utils.GoogleButtonStyleParser
import com.androiddev.profilehub.utils.MIN_WIDTH_GOOGLE_BUTTON
import com.androiddev.profilehub.utils.dpToPx
import kotlin.math.max
import kotlin.properties.Delegates

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
    private val typefaceGoogle = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold)

    private val blueColor = ContextCompat.getColor(context, R.color.colorBlueGoogle)
    private val redColor = ContextCompat.getColor(context, R.color.colorRedGoogle)
    private val greenColor = ContextCompat.getColor(context, R.color.colorGreenGoogle)
    private val yellowColor = ContextCompat.getColor(context, R.color.colorYellowGoogle)

    private val googleColors = listOf(
        blueColor,
        redColor,
        yellowColor,
        blueColor,
        greenColor,
        redColor
    )

    private lateinit var text: String
    private var textSizeGoogleSp by Delegates.notNull<Float>()
    private var letterSpacingGooglePx by Delegates.notNull<Float>()
    private val textPaint: Paint

    private val rectRound = RectF()
    private val rectBitmap = RectF()

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = bgColor
        style = Paint.Style.FILL
    }

    init {
        parseAttributes(context, attrs, defStyleAttr)

        isClickable = true
        contentDescription = context.getString(R.string.desc_google)

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = textSizeGoogleSp
            typeface = typefaceGoogle
        }
    }

    private fun parseAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
    ) {
        val style = GoogleButtonStyleParser.parse(context, attrs, defStyleAttr)
        text = if (style.textAllCaps) style.text.uppercase() else style.text
        textSizeGoogleSp = style.textSize
        letterSpacingGooglePx = style.letterSpacing
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val minWidth = MIN_WIDTH_GOOGLE_BUTTON.dpToPx(context).toInt()
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> minWidth.coerceAtMost(size)
            MeasureSpec.UNSPECIFIED -> minWidth
            else -> minWidth
        }
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val minHeight = max(minHeightPx, suggestedMinimumHeight)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> minHeight.coerceAtMost(size)
            MeasureSpec.UNSPECIFIED -> minHeight
            else -> minHeight
        }
    }

    private fun getCenterY() = height / 2f

    private fun getTextStartX(): Float {
        val textWidth = textPaint.measureText(text)
        return (width - textWidth) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBackground(canvas)

        val centerY = getCenterY()
        val textStartX = getTextStartX()

        drawGoogleIcon(canvas, textStartX, centerY)
        drawColoredText(canvas, textStartX, centerY)
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

    private fun drawColoredText(canvas: Canvas, startX: Float, centerY: Float) {
        val textBaseline = centerY - (textPaint.descent() + textPaint.ascent()) / 2
        var x = startX

        for ((index, char) in text.withIndex()) {
            textPaint.color = googleColors[index % googleColors.size]
            val charStr = char.toString()

            canvas.drawText(charStr, x, textBaseline, textPaint)
            x += textPaint.measureText(charStr) + letterSpacingGooglePx
        }
    }
}