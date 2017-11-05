package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Settings {

    interface View : Contracts.View {
        val logoutIntent: Observable<Unit>
        val photoCountChangeIntent: Observable<Int>
        val photoSequenceIntervalChangeIntent: Observable<Int>

        fun render(viewState: Contracts.ViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<SettingsViewState>

        fun attachLogoutIntent(intentObservable: Observable<Unit>)
        fun attachPhotoCountChangeIntent(intentObservable: Observable<Int>)
        fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>)
    }

    interface Navigator : Contracts.Navigator {
        fun showSplashScreen()
    }
}