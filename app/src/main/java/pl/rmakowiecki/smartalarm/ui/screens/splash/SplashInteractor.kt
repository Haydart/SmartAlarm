package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthService
import java.util.concurrent.TimeUnit

class SplashInteractor(
        private val authService: AuthService,
        private val navigator: Splash.Navigator
) : Splash.Interactor {

    override fun getViewStateObservable(): Observable<SplashViewState> = Observable.empty()

    override fun attachTransitionIntent(intentObservable: Observable<Unit>) {
        getViewStateObservable()
                .mergeWith(intentObservable
                        .map { SplashViewState.afterTransition() }
                        .delay(1, TimeUnit.SECONDS))
                .mergeWith(
                        Observable.timer(2, TimeUnit.SECONDS)
                                .flatMapSingle { authService.isUserLoggedIn() }
                                .doOnNext(this::navigateToProperScreen)
                                .map { SplashViewState.afterTransition() }
                )
    }

    private fun navigateToProperScreen(isUserLoggedIn: Boolean) =
            if (isUserLoggedIn) navigator.showHomeScreen()
            else navigator.showAuthScreen()
}