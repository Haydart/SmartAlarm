package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import io.reactivex.Single
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
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SettingsViewState() }
        )
    }

    override fun attachPhotoSequenceIntervalChangeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SettingsViewState() }
        )
    }
}

class FirebaseSettingsService : SettingsService {
    override fun fetchPhotoCountValue(): Single<SingleSettingResult> {
        //todo implement
    }

    override fun fetchPhotoSequenceIntervalValue(): Single<SingleSettingResult> {
        //todo implement
    }

    override fun sendPhotoCountValue(value: Int) {
        //todo implement
    }

    override fun sendPhotoSequenceIntervalValue(value: Int) {
        //todo implement
    }
}

interface SettingsService {

    fun sendPhotoCountValue(value: Int)
    fun sendPhotoSequenceIntervalValue(value: Int)
    fun fetchPhotoCountValue(): Single<SingleSettingResult>
    fun fetchPhotoSequenceIntervalValue(): Single<SingleSettingResult>
}

class SingleSettingResult(
        val isSuccessful: Boolean,
        val singleValue: Int
)
