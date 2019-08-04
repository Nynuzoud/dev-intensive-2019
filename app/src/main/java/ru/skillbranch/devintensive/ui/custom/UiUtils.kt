package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.R


object UiUtils {

    fun writeOnDrawable(context: Context, text: String): BitmapDrawable {

        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(ContextCompat.getColor(context, R.color.color_accent))
        drawable.setSize(
            context.resources.getDimensionPixelSize(R.dimen.avatar_round_size_112),
            context.resources.getDimensionPixelSize(R.dimen.avatar_round_size_112))

        val bm = getBitmapFromDrawable(drawable)

        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        paint.textSize = context.resources.getDimension(R.dimen.font_extra_large_48)

        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)

        val canvas = Canvas(bm)
        canvas.drawText(text, ((bm?.width ?: 0) / 2f) - (textBounds.width() / 2), ((bm?.height ?: 0) / 2f) + (textBounds.height() / 2), paint)

        return BitmapDrawable(context.resources, bm)
    }

    fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
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
}