package pl.rmakowiecki.smartalarm.ui.customView

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.graphics.drawable.DrawableWrapper

class TilingDrawable(drawable: Drawable) : DrawableWrapper(drawable) {

    private var callbackEnabled = true

    override fun draw(canvas: Canvas) {
        callbackEnabled = false
        val bounds = bounds
        val wrappedDrawable = wrappedDrawable

        val width = wrappedDrawable.intrinsicWidth
        val height = wrappedDrawable.intrinsicHeight
        var x = bounds.left
        while (x < bounds.right + width - 1) {
            var y = bounds.top
            while (y < bounds.bottom + height - 1) {
                wrappedDrawable.setBounds(x, y, x + width, y + height)
                wrappedDrawable.draw(canvas)
                y += height
            }
            x += width
        }
        callbackEnabled = true
    }

    override fun onBoundsChange(bounds: Rect) = Unit

    override fun invalidateDrawable(who: Drawable?) {
        if (callbackEnabled) {
            super.invalidateDrawable(who)
        }
    }

    override fun scheduleDrawable(who: Drawable?, what: Runnable, `when`: Long) {
        if (callbackEnabled) {
            super.scheduleDrawable(who, what, `when`)
        }
    }

    override fun unscheduleDrawable(who: Drawable?, what: Runnable) {
        if (callbackEnabled) {
            super.unscheduleDrawable(who, what)
        }
    }
}