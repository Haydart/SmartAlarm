package pl.rmakowiecki.smartalarm.customView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ExtendedViewPager @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun canScroll(view: View, checkV: Boolean, dx: Int, x: Int, y: Int) =
            (view as? TouchImageView)?.canScrollHorizontally(-dx) ?: super.canScroll(view, checkV, dx, x, y)
}

class TouchImageViewAdapter(
        private val context: Context,
        var photoUrls: List<String>,
        private val singleTapListener: SingleTapListener
) : PagerAdapter() {

    override fun getCount() = photoUrls.size

    override fun instantiateItem(container: ViewGroup, position: Int) = TouchImageView(container.context).apply {
        setOnSingleTapListener(singleTapListener)

        Picasso.with(context)
                .load(photoUrls[position])
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit

                    override fun onBitmapFailed(errorDrawable: Drawable?) = Unit

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) =
                            this@apply.setImageBitmap(bitmap)
                })

        container.addView(this, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) =
            container!!.removeView(`object` as View)

    override fun isViewFromObject(view: View?, `object`: Any) = view === `object`
}