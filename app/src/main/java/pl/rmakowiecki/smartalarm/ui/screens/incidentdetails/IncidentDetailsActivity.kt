package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_incident_details.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.logD
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.customView.DepthPageTransformer
import pl.rmakowiecki.smartalarm.ui.customView.SingleTapListener
import pl.rmakowiecki.smartalarm.ui.customView.TouchImageViewAdapter

private const val AUTO_HIDE = false
private const val AUTO_HIDE_DELAY_MILLIS = 3000
private const val UI_ANIMATION_DELAY = 300

class IncidentDetailsActivity : AppCompatActivity() {

    private var mContentView: View? = null
    private var mControlsView: View? = null
    private var menuControlsVisible: Boolean = false

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val delayHideTouchListener = View.OnTouchListener { view, motionEvent ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    private val mHidePart2Runnable = Runnable {
        mContentView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        val actionBar = supportActionBar
        actionBar?.show()
        mControlsView!!.visibility = View.VISIBLE
    }

    private val mHideHandler = Handler()
    private val mHideRunnable = Runnable { hide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident_details)

        menuControlsVisible = true
        mControlsView = findViewById(R.id.fullscreen_content_controls)
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

        // Set up the user interaction to manually show or hide the system UI.
        contentViewPager.setOnClickListener {
            logD("clicked viewpager")
            toggle()
        }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById<Button>(R.id.dummy_button).setOnTouchListener(delayHideTouchListener)
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
        // Show the system bar
        mContentView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
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
