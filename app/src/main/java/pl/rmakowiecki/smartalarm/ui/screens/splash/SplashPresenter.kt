package pl.rmakowiecki.smartalarm.ui.screens.splash

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class SplashPresenter(
        private val interactor: Splash.Interactor
) : MviPresenter<Splash.View, SplashViewState>() {

    override fun bindIntents() = with(interactor) {
        attachTransitionIntent(
                bindIntent(Splash.View::splashTransitionIntent))

        subscribeViewState(viewStateIntentObservable, Splash.View::render)
    }
}