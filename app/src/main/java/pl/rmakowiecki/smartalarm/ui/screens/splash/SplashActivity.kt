package pl.rmakowiecki.smartalarm.ui.screens.splash

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.ui.screens.customView.TilingDrawable

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setActivityBackground()
        Handler().postDelayed({ startLogoAnimation() }, 750)
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        rootLayout.background = tilingDrawable
    }

    private fun startLogoAnimation() {
        val transitionSet = TransitionInflater
                .from(this)
                .inflateTransition(R.transition.splash_logo) as TransitionSet
        TransitionManager.beginDelayedTransition(rootLayout, transitionSet)
        splashLogoInscription.visibility = View.VISIBLE
    }
}
