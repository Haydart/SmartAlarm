package pl.rmakowiecki.smartalarm.ui.screens.splash

import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_splash.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable
import javax.inject.Inject

class SplashActivity : MviActivity<Contracts.View, Contracts.ViewState, SplashPresenter>() {

    @Inject lateinit var presenter: SplashPresenter

    override val layoutRes = R.layout.activity_splash

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.overlay_pattern_background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        contentLayout.background = tilingDrawable
    }
}