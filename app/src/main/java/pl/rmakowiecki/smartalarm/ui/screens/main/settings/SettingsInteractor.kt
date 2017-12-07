package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.data.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.data.main.settings.FirebaseSettingsService
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsViewStateChange.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val SEQUENCE_INTERVAL_MULTIPLIER = 50
const val SEQUENCE_INTERVAL_OFFSET = 200
const val PHOTO_COUNT_OFFSET = 4
private const val LOADER_HIDE_DELAY_MILLIS = 500L

class SettingsInteractor @Inject constructor(
        private val authService: FirebaseAuthService,
        private val settingsService: FirebaseSettingsService,
        private val reducer: SettingsViewStateReducer,
        private val navigator: SettingsNavigator
) {

    private var viewStateObservable = Observable.empty<SettingsViewStateChange>()

    fun getViewStateObservable(): Observable<SettingsViewState> = viewStateObservable
            .scan(SettingsViewState(), reducer::reduce)

    fun attachLogoutButtonClickIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMapMaybe { navigator.showLogoutConfirmationDialog() }
                        .flatMapSingle { authService.logoutUser() }
                        .doOnNext { navigator.showSplashScreen() }
                        .flatMap { Observable.empty<SettingsViewStateChange>() }
                )
    }

    fun attachPhotoCountInfoIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .doOnNext { navigator.showPhotoCountInfoDialog() }
                        .flatMap { Observable.empty<SettingsViewStateChange>() }
                )
    }

    fun attachSequenceIntervalInfoIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .doOnNext { navigator.showSequenceIntervalInfoDialog() }
                        .flatMap { Observable.empty<SettingsViewStateChange>() }
                )
    }

    fun attachPhotoCountChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map { PhotoCountValueChange(it + PHOTO_COUNT_OFFSET) }
                )
                .mergeWith(intentObservable
                        .map { it + PHOTO_COUNT_OFFSET }
                        .switchMapSingle(settingsService::sendPhotoCountValue)
                        .debounce(LOADER_HIDE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                        .map { PhotoCountChangeComplete() }
                )
    }

    fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map { SequenceIntervalValueChange(it * SEQUENCE_INTERVAL_MULTIPLIER + SEQUENCE_INTERVAL_OFFSET) }
                )
                .mergeWith(intentObservable
                        .map { it * SEQUENCE_INTERVAL_MULTIPLIER + SEQUENCE_INTERVAL_OFFSET }
                        .switchMapSingle(settingsService::sendPhotoSequenceIntervalValue)
                        .debounce(LOADER_HIDE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                        .map { SequenceIntervalChangeComplete() }
                )
    }
}