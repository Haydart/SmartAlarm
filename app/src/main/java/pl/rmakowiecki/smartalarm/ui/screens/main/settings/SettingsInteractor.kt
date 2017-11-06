package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsViewStateChange.*
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
        private val authService: FirebaseAuthService,
        private val settingsService: FirebaseSettingsService,
        private val reducer: SettingsViewStateReducer,
        private val navigator: SettingsNavigator
) : Settings.Interactor {

    private var viewStateObservable = Observable.empty<SettingsViewStateChange>()

    override fun getViewStateObservable(): Observable<SettingsViewState> = viewStateObservable
            .scan(SettingsViewState(), reducer::reduce)

    override fun attachLogoutButtonClickIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMap { navigator.showLogoutConfirmationDialog() }
                )
    }

    override fun attachLogoutDialogOkClickIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMapSingle { authService.logoutUser() }
                        .doOnNext { navigator.showSplashScreen() }
                        .flatMap { Observable.empty<SettingsViewStateChange>() }
                )
    }

    override fun attachPhotoCountInfoIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .doOnNext { navigator.showPhotoCountInfoDialog() }
                        .flatMap { Observable.empty<SettingsViewStateChange>() }
                )
    }

    override fun attachSequenceIntervalInfoIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .doOnNext { navigator.showSequenceIntervalInfoDialog() }
                        .flatMap { Observable.empty<SettingsViewStateChange>() }
                )
    }

    override fun attachPhotoCountChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map { PhotoCountValueChange(it) }
                )
                .mergeWith(intentObservable
                        .switchMapSingle(settingsService::sendPhotoCountValue)
                        .map { PhotoCountChangeComplete() }
                )
    }

    override fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map { SequenceIntervalValueChange(it) }
                )
                .mergeWith(intentObservable
                        .switchMapSingle(settingsService::sendPhotoSequenceIntervalValue)
                        .map { SequenceIntervalChangeComplete() }
                )
    }
}