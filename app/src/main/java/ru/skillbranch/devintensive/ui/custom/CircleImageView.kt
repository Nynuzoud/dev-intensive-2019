package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.R
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

    private var mBitmapShader: Shader? = null
    private var mBitmap: Bitmap? = null
    private var mInitialized: Boolean
    private val mStrokePaint: Paint
    private var mBitmapDrawBounds: RectF
    private var mStrokeBounds: RectF
    private var mBitmapPaint: Paint
    private var mShaderMatrix: Matrix
    private var strokeColor: Int = ContextCompat.getColor(context, DEFAULT_COLOR)
    private var strokeWidth: Float = resources.getDimension(DEFAULT_BORDER_WIDTH)

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            strokeColor =
                a.getColor(
                    R.styleable.CircleImageView_cv_borderColor,
                    ContextCompat.getColor(context, DEFAULT_COLOR)
                )

            strokeWidth =
                a.getDimension(
                    R.styleable.CircleImageView_cv_borderWidth,
                    resources.getDimension(DEFAULT_BORDER_WIDTH)
                )

            a.recycle()
        }

        mShaderMatrix = Matrix()
        mBitmapPaint = Paint(ANTI_ALIAS_FLAG)
        mStrokePaint = Paint(ANTI_ALIAS_FLAG)
        mStrokeBounds = RectF()
        mBitmapDrawBounds = RectF()
        mStrokePaint.color = strokeColor
        mStrokePaint.style = Paint.Style.STROKE
        mStrokePaint.strokeWidth = strokeWidth

        mInitialized = true

        setPadding(strokeWidth.toInt(), strokeWidth.toInt(), strokeWidth.toInt(), strokeWidth.toInt())
    }

    private fun setupBitmap() {
        if (!mInitialized) {
            return
        }
        mBitmap = getBitmapFromDrawable(drawable)
        if (mBitmap == null) {
            return
        }

        mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBitmapPaint.shader = mBitmapShader

        updateBitmapSize()
    }

    private fun updateBitmapSize() {
        if (mBitmap == null) return

        val dx: Float
        val dy: Float
        val scale: Float

        if (mBitmap?.width ?: 0 < mBitmap?.height ?: 0) {
            scale = mBitmapDrawBounds.width() / (mBitmap?.width?.toFloat() ?: 0f)
            dx = mBitmapDrawBounds.left
            dy =
                mBitmapDrawBounds.top - (mBitmap?.height ?: 0) * scale / 2f + mBitmapDrawBounds.width() / 2f
        } else {
            scale = mBitmapDrawBounds.height() / (mBitmap?.height?.toFloat() ?: 0f)
            dx =
                mBitmapDrawBounds.left - (mBitmap?.width ?: 0) * scale / 2f + mBitmapDrawBounds.width() / 2f
            dy = mBitmapDrawBounds.top
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(dx, dy)
        mBitmapShader?.setLocalMatrix(mShaderMatrix)
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onDraw(canvas: Canvas?) {
        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }
        val b = getBitmapFromDrawable(drawable)
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
        borderPaint.strokeWidth = strokeWidth
        borderPaint.color = strokeColor

        canvas.drawCircle(canvas.width.toFloat() / 2, canvas.width.toFloat() / 2, newBitmapSquareWidth.toFloat() / 2 - strokeWidth, borderPaint)

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
        paint.color = strokeColor
        canvas.drawCircle(
            radius / 2 + 0.7f, radius / 2 + 0.7f,
            radius / 2 + 0.1f, paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(sbmp, rect, rect, paint)

        return output
    }
}