package pl.rmakowiecki.smartalarm.ui.screens.splash

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable
import javax.inject.Inject

class SplashActivity : MviActivity<Splash.View, SplashViewState, SplashPresenter>(),
        Splash.View {

    @Inject lateinit var presenter: SplashPresenter

    override val layoutRes = R.layout.activity_splash

    override val splashTransitionIntent: Observable<Unit> =
            Observable.just(Unit)

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        contentLayout.background = tilingDrawable
    }

    override fun render(viewState: SplashViewState) {
        if (viewState.afterTransition) startLogoAnimation()
    }

    private fun startLogoAnimation() {
        val transitionSet = TransitionInflater
                .from(this)
                .inflateTransition(R.transition.splash_logo) as TransitionSet
        TransitionManager.beginDelayedTransition(rootLayout, transitionSet)
        splashLogoInscription.visibility = View.VISIBLE
    }
}