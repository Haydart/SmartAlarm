package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Maybe
import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Settings {

    interface View : Contracts.View {
        val logoutButtonClickIntent: Observable<Unit>
        val photoCountChangeIntent: Observable<Int>
        val photoSequenceIntervalChangeIntent: Observable<Int>
        val photoCountInfoIntent: Observable<Unit>
        val sequenceIntervalInfoIntent: Observable<Unit>

        fun render(viewState: Contracts.ViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<SettingsViewState>

        fun attachLogoutButtonClickIntent(intentObservable: Observable<Unit>)
        fun attachPhotoCountInfoIntent(intentObservable: Observable<Unit>)
        fun attachSequenceIntervalInfoIntent(intentObservable: Observable<Unit>)
        fun attachPhotoCountChangeIntent(intentObservable: Observable<Int>)
        fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>)
    }

    interface Navigator : Contracts.Navigator {
        fun showPhotoCountInfoDialog()
        fun showSequenceIntervalInfoDialog()
        fun showLogoutConfirmationDialog(): Maybe<Boolean>
        fun showSplashScreen()
    }
}