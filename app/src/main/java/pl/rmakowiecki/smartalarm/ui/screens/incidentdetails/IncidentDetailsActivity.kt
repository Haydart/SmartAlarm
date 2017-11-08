package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_incident_details.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.gone
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.extensions.visible
import pl.rmakowiecki.smartalarm.ui.customView.DepthPageTransformer
import pl.rmakowiecki.smartalarm.ui.customView.SingleTapListener
import pl.rmakowiecki.smartalarm.ui.customView.TouchImageViewAdapter

private const val UI_ANIMATION_DELAY = 300

class IncidentDetailsActivity : AppCompatActivity() {

    private var menuControlsVisible: Boolean = false
    private val hideLayoutsHandler = Handler()

    private val hideDelayedRunnable = Runnable {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        appbar.gone()
        incidentInfoBottomLayout.gone()
    }

    private val showDelayedRunnable = Runnable {
        appbar.visible()
        incidentInfoBottomLayout.visible()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident_details)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


        statusBarMock.layoutParams.height = getStatusBarHeight()

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Incident"
        }

        menuControlsVisible = true

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

    private fun getStatusBarHeight() = resources.getDimensionPixelSize(
            resources.getIdentifier("status_bar_height", "dimen", "android")
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() = if (menuControlsVisible) hide() else show()

    private fun hide() {
        menuControlsVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideLayoutsHandler.removeCallbacks(showDelayedRunnable)
        hideLayoutsHandler.postDelayed(hideDelayedRunnable, UI_ANIMATION_DELAY.toLong())
    }

    @SuppressLint("InlinedApi")
    private fun show() {
        menuControlsVisible = true

        // Schedule a runnable to display UI elements after a delay
        hideLayoutsHandler.removeCallbacks(hideDelayedRunnable)
        hideLayoutsHandler.postDelayed(showDelayedRunnable, UI_ANIMATION_DELAY.toLong())
    }

    companion object {
        fun start(context: Context) = context.startActivity<IncidentDetailsActivity>()
    }
}
