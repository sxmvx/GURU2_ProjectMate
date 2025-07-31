package com.example.projectmate

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable

class DotDrawable(private val color: Int) : Drawable() {
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        this.color = this@DotDrawable.color
    }

    override fun draw(canvas: Canvas) {
        val radius = Math.min(bounds.width(), bounds.height()) / 12f
        val cx = bounds.centerX().toFloat()
        val cy = bounds.bottom.toFloat() - radius
        canvas.drawCircle(cx, cy, radius, paint)
    }

    override fun setAlpha(alpha: Int) { paint.alpha = alpha }
    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) { paint.colorFilter = colorFilter }

    @Suppress("DEPRECATION") // 오류 방지용...
    @Deprecated("Deprecated by Android SDK") // 오류 방지용 22...
    override fun getOpacity(): Int = android.graphics.PixelFormat.TRANSLUCENT
}
