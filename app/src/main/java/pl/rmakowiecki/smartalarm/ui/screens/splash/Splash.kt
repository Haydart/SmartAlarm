package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Splash {

    interface Navigator : Contracts.Navigator {
        fun showAuthScreen()
        fun showHomeScreen()
        fun startLogoTransition()
    }

    interface Interactor : Contracts.Interactor {
        val stubIntentObservable: Observable<Contracts.ViewState>
    }
}