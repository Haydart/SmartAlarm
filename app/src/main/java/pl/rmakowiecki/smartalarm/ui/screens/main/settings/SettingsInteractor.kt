package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
        private val authService: FirebaseAuthService,
        private val settingsService: FirebaseSettingsService,
        private val navigator: SettingsNavigator
) : Settings.Interactor {

    private var viewStateObservable = Observable.empty<SettingsViewState>()

    override fun getViewStateObservable(): Observable<SettingsViewState> = viewStateObservable

    override fun attachLogoutIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMapSingle { authService.logoutUser() }
                        .doOnNext { navigator.showSplashScreen() }
                        .flatMap { Observable.empty<SettingsViewState>() }
                )
    }

    override fun attachPhotoCountInfoIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SettingsViewState() }
        )
    }

    override fun attachSequenceIntervalInfoIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SettingsViewState() }
        )
    }

    override fun attachPhotoCountChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .switchMapSingle(settingsService::sendPhotoCountValue)
                        .map { SettingsViewState() }
                )
    }

    override fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .switchMapSingle(settingsService::sendPhotoSequenceIntervalValue)
                        .map { SettingsViewState() }
                )
    }
}