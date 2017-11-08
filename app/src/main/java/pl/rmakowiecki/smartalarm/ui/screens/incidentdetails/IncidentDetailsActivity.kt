package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.jakewharton.rxbinding2.support.v4.view.pageSelections
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_incident_details.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.customView.DepthPageTransformer
import pl.rmakowiecki.smartalarm.ui.customView.SingleTapListener
import pl.rmakowiecki.smartalarm.ui.customView.TouchImageViewAdapter
import javax.inject.Inject

private const val UI_ANIMATION_DELAY = 150
private const val UI_ANIMATION_DURATION = 150L

class IncidentDetailsActivity : MviActivity<IncidentDetails.View, IncidentDetailsViewState, IncidentDetailsPresenter>(),
        IncidentDetails.View {

    @Inject lateinit var presenter: IncidentDetailsPresenter

    override val layoutRes = R.layout.activity_incident_details

    override val photoSwipeIntent: Observable<Int>
        get() = contentViewPager.pageSelections()

    private var menuControlsVisible: Boolean = true
    private val hideLayoutsHandler = Handler()

    private val hideDelayedRunnable = Runnable {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        appbar.animate()
                .translationYBy(-getStatusBarHeight().toFloat() * 5)
                .setDuration(UI_ANIMATION_DURATION)
                .start()
        incidentInfoBottomLayout.animate()
                .translationYBy(getStatusBarHeight().toFloat() * 5)
                .setDuration(UI_ANIMATION_DURATION)
                .start()
    }

    private val showDelayedRunnable = Runnable {
        appbar.animate()
                .translationYBy(getStatusBarHeight().toFloat() * 5)
                .setDuration(UI_ANIMATION_DURATION)
                .start()
        incidentInfoBottomLayout.animate()
                .translationYBy(-getStatusBarHeight().toFloat() * 5)
                .setDuration(UI_ANIMATION_DURATION)
                .start()
    }

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        setupContent()
        setupWindowFlags()
    }

    private fun setupWindowFlags() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Incident"
        }
    }

    private fun setupContent() {
        statusBarMock.layoutParams.height = getStatusBarHeight()

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

    override fun render(viewState: IncidentDetailsViewState) {
        //todo implement
    }

    private fun toggle() = if (menuControlsVisible) hide() else show()

    private fun hide() {
        menuControlsVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideLayoutsHandler.removeCallbacks(showDelayedRunnable)
        hideLayoutsHandler.postDelayed(hideDelayedRunnable, UI_ANIMATION_DELAY.toLong())
    }

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