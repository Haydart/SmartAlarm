package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
        private val authService: FirebaseAuthService,
        private val navigator: SettingsNavigator
) : Settings.Interactor {

    private var viewStateObservable = Observable.empty<Contracts.ViewState>()

    override fun getViewStateObservable(): Observable<Contracts.ViewState> = viewStateObservable

    override fun attachLogoutIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMapSingle { authService.logoutUser() }
                        .doOnNext { navigator.showSplashScreen() }
                        .flatMap { Observable.empty<Contracts.ViewState>() }
                )
    }

}