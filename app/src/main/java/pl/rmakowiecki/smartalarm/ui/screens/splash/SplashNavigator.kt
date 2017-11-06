package pl.rmakowiecki.smartalarm.ui.screens.splash

import android.app.Activity
import android.transition.Explode
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.Extra
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthActivity
import pl.rmakowiecki.smartalarm.ui.screens.main.HomeActivity
import javax.inject.Inject

class SplashNavigator @Inject constructor(
        private val activity: Activity
) : Splash.Navigator {

    override fun showAuthScreen() = with(activity as SplashActivity) {
        startActivity<AuthActivity>(
                Extra.SharedView(splashLogo),
                Extra.SharedView(contentView)
        )
        overridePendingTransition(0, 0)
        window.exitTransition = Explode()
    }

    override fun showHomeScreen() = with(activity as SplashActivity) {
        startActivity<HomeActivity>()
        overridePendingTransition(0, 0)
        window.exitTransition = Explode()
    }

    override fun startLogoTransition() {
        val transitionSet = TransitionInflater
                .from(activity)
                .inflateTransition(R.transition.splash_logo) as TransitionSet
        TransitionManager.beginDelayedTransition(activity.rootLayout, transitionSet)
        activity.splashLogoInscription.visibility = View.VISIBLE
    }
}