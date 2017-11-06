package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsViewStateChange.*
import javax.inject.Inject

const val SEQUENCE_INTERVAL_MULTIPLIER = 50
const val SEQUENCE_INTERVAL_OFFSET = 200
const val PHOTO_COUNT_OFFSET = 4

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
                        .flatMapMaybe { navigator.showLogoutConfirmationDialog() }
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
                        .map { PhotoCountValueChange(it + PHOTO_COUNT_OFFSET) }
                )
                .mergeWith(intentObservable
                        .map { it + PHOTO_COUNT_OFFSET }
                        .switchMapSingle(settingsService::sendPhotoCountValue)
                        .map { PhotoCountChangeComplete() }
                )
    }

    override fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map { SequenceIntervalValueChange(it * SEQUENCE_INTERVAL_MULTIPLIER + SEQUENCE_INTERVAL_OFFSET) }
                )
                .mergeWith(intentObservable
                        .map { it * SEQUENCE_INTERVAL_MULTIPLIER + SEQUENCE_INTERVAL_OFFSET }
                        .switchMapSingle(settingsService::sendPhotoSequenceIntervalValue)
                        .map { SequenceIntervalChangeComplete() }
                )
    }
}