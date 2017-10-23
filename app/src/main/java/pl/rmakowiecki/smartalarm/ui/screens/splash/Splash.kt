package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Splash {

    interface View : Contracts.View {
        val splashTransitionIntent: Observable<Unit>

        fun render(viewState: SplashViewState)
    }

    interface Navigator : Contracts.Navigator {
        fun showAuthScreen()
        fun showHomeScreen()
    }

    interface Interactor : Contracts.Interactor {
        fun attachTransitionIntent(intentObservable: Observable<Unit>)

        fun getViewStateObservable(): Observable<SplashViewState>
    }
}