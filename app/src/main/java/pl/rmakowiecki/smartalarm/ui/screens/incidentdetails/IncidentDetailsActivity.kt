package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_incident_details.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.customView.DepthPageTransformer
import pl.rmakowiecki.smartalarm.ui.customView.SingleTapListener
import pl.rmakowiecki.smartalarm.ui.customView.TouchImageViewAdapter

private const val UI_ANIMATION_DELAY = 300

class IncidentDetailsActivity : AppCompatActivity() {

    private var mContentView: View? = null
    private var mControlsView: View? = null
    private var menuControlsVisible: Boolean = false

    private val mHidePart2Runnable = Runnable {
        mContentView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    private val mShowPart2Runnable = Runnable {
        mControlsView!!.visibility = View.VISIBLE
    }

    private val mHideHandler = Handler()
    private val mHideRunnable = Runnable { hide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident_details)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        menuControlsVisible = true
        mControlsView = findViewById(R.id.incidentInfoBar)
        mContentView = findViewById(R.id.contentViewPager)

        contentViewPager.adapter = TouchImageViewAdapter(
                this,
                listOf(
                        "http://www.fungilab.com/imagenes/APM_03.jpg",
                        "http://www.osuinternationalhouse.com/wp-content/uploads/2011/10/logo_house_small.png",
                        "http://imgsv.imaging.nikon.com/lineup/lens/zoom/normalzoom/af-s_dx_18-140mmf_35-56g_ed_vr/img/sample/sample1_l.jpg",
                        "http://www.saraeichner.com/eichnerpaintingspage3/greenverticlewallpaper.jpg"
                ),
                object : SingleTapListener {
                    override fun onSingleTapPerformed() = toggle()
                })
        contentViewPager.setPageTransformer(true, DepthPageTransformer())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() = if (menuControlsVisible) hide() else show()

    private fun hide() {
        // Hide UI first
        val actionBar = supportActionBar
        actionBar?.hide()
        mControlsView!!.visibility = View.GONE
        menuControlsVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    @SuppressLint("InlinedApi")
    private fun show() {
        menuControlsVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        fun start(context: Context) = context.startActivity<IncidentDetailsActivity>()
    }
}
