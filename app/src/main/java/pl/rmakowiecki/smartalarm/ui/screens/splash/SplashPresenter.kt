package pl.rmakowiecki.smartalarm.ui.screens.splash

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class SplashPresenter(
        private val interactor: Splash.Interactor
) : MviPresenter<Contracts.View, Contracts.ViewState>() {

    override fun bindIntents() = with(interactor) {
        attachTransitionIntent(
                bindIntent(Splash.View::splashTransitionIntent))

        subscribeViewState(getViewStateObservable(), Splash.View::render)
    }
}