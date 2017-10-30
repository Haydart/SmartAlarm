package pl.rmakowiecki.smartalarm.ui.customView

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class SquareImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(measuredWidth: Int, measuredHeight: Int) {
        super.onMeasure(measuredWidth, measuredHeight)
        val commonMeasureSpec = if (measuredWidth > measuredHeight) measuredHeight else measuredWidth
        setMeasuredDimension(commonMeasureSpec, commonMeasureSpec)
    }
}
