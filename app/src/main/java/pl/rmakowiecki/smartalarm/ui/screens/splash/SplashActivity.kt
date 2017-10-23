package pl.rmakowiecki.smartalarm.ui.screens.splash

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.transition.Explode
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.extensions.Extra
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthActivity
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.ui.screens.main.HomeActivity

class SplashActivity : MviActivity<Contracts.View, Contracts.ViewState, SplashPresenter>(),
        Splash.View, Splash.Navigator {

    override val splashTransitionIntent: Observable<Unit>
        get() = Observable.just(Unit)

    override val layoutRes = R.layout.activity_splash

    override fun createPresenter() = SplashPresenter(
            SplashInteractor(
                    FirebaseAuthService(),
                    this
            )
    )

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

    override fun showAuthScreen() {
        startActivity<AuthActivity>(
                Extra.SharedView(splashLogo),
                Extra.SharedView(contentView)
        )
        overridePendingTransition(0, 0)
        window.exitTransition = Explode()
    }

    override fun showHomeScreen() {
        startActivity<HomeActivity>()
        overridePendingTransition(0, 0)
        window.exitTransition = Explode()
    }
}