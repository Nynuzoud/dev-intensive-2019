package ru.skillbranch.devintensive.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable

class TextDrawable(private val text: String) : Drawable() {

    private var paint: Paint = Paint()

    init {
        paint.color = Color.WHITE
        paint.textSize = 22f
        paint.isAntiAlias = true
        paint.isFakeBoldText = true
        paint.setShadowLayer(6f, 0f, 0f, Color.BLACK)
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.LEFT
    }

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, 0f, 0f, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}