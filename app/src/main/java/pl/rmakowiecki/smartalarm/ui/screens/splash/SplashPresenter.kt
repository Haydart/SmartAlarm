package pl.rmakowiecki.smartalarm.ui.screens.splash

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
        private val interactor: SplashInteractor
) : MviPresenter<Splash.View, SplashViewState>() {

    override fun bindIntents() = with(interactor) {
        attachTransitionIntent(
                bindIntent(Splash.View::splashTransitionIntent))

        subscribeViewState(viewStateIntentObservable, Splash.View::render)
    }
}