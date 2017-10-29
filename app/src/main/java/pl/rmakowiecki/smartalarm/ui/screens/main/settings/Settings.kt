package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Settings {

    interface View : Contracts.View {
        val logoutIntent: Observable<Unit>

        fun render(viewState: Contracts.ViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<Contracts.ViewState>

        fun attachLogoutIntent(intentObservable: Observable<Unit>)
    }

    interface Navigator : Contracts.Navigator {
        fun showSplashScreen()
    }
}