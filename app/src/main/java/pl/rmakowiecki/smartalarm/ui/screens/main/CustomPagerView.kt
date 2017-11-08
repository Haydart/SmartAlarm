package pl.rmakowiecki.smartalarm.ui.screens.main

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent

class BottomBarViewPager @JvmOverloads constructor(
        context: Context,
        attributes: AttributeSet
) : ViewPager(context, attributes) {

    private val isPagingEnabled: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.isPagingEnabled && super.onTouchEvent(event)
    }

    //for samsung phones to prevent tab switching keys to show on keyboard
    override fun executeKeyEvent(event: KeyEvent): Boolean =
            isPagingEnabled && super.executeKeyEvent(event)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean =
            this.isPagingEnabled && super.onInterceptTouchEvent(event)
}

