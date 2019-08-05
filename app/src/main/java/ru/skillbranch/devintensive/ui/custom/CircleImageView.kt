package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import kotlin.math.min

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_COLOR = android.R.color.white
        const val DEFAULT_BORDER_WIDTH = R.dimen.cv_border_width_2
    }

    private var borderColor: Int = ContextCompat.getColor(context, DEFAULT_COLOR)
    private var borderWidth: Int = resources.getDimensionPixelSize(DEFAULT_BORDER_WIDTH)

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor =
                a.getColor(
                    R.styleable.CircleImageView_cv_borderColor,
                    ContextCompat.getColor(context, DEFAULT_COLOR)
                )

            borderWidth =
                a.getDimensionPixelSize(
                    R.styleable.CircleImageView_cv_borderWidth,
                    resources.getDimensionPixelSize(DEFAULT_BORDER_WIDTH)
                )

            a.recycle()
        }
    }

    @Dimension
    fun getBorderWidth(): Int = Utils.convertPxToDp(context, borderWidth)

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = Utils.convertDpToPx(context, dp.toFloat())
        invalidate()
    }

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = ContextCompat.getColor(context, colorId)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }
        val b = UiUtils.getBitmapFromDrawable(drawable)
        val bitmap = b?.copy(Bitmap.Config.ARGB_8888, true) ?: return

        val w = width

        val roundBitmap = getCroppedBitmap(bitmap, w)
        val borderedBitmap = getBorderedMitmap(roundBitmap)
        canvas?.drawBitmap(borderedBitmap, 0f, 0f, null)
    }

    private fun getBorderedMitmap(bitmap: Bitmap): Bitmap {
        val canvas = Canvas(bitmap)
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        val borderWidthHalf = 10

        val bitmapSquareWidth = min(bitmapWidth, bitmapHeight)
        val newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf

        val borderPaint = Paint()
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth.toFloat()
        borderPaint.color = borderColor

        canvas.drawCircle(canvas.width.toFloat() / 2, canvas.width.toFloat() / 2, newBitmapSquareWidth.toFloat() / 2 - borderWidth, borderPaint)

        return bitmap
    }

    private fun getCroppedBitmap(bmp: Bitmap, radius: Int): Bitmap {
        val sbmp: Bitmap

        sbmp = if (bmp.width != radius || bmp.height != radius) {
            val smallest = min(bmp.width, bmp.height).toFloat()
            val factor = smallest / radius
            Bitmap.createScaledBitmap(
                bmp,
                (bmp.width / factor).toInt(),
                (bmp.height / factor).toInt(), false
            )
        } else {
            bmp
        }

        val output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        val rect = Rect(0, 0, radius, radius)

        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = borderColor
        canvas.drawCircle(
            radius / 2 + 0.7f, radius / 2 + 0.7f,
            radius / 2 + 0.1f, paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(sbmp, rect, rect, paint)

        return output
    }
}