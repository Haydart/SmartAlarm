package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.extensions.applyIoSchedulers
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthService
import java.util.concurrent.TimeUnit

class SplashInteractor(
        private val authService: AuthService,
        private val navigator: Splash.Navigator
) : Splash.Interactor {

    override var viewStateIntentObservable: Observable<SplashViewState> = Observable.empty()
        private set

    override fun attachTransitionIntent(intentObservable: Observable<Unit>) {
        viewStateIntentObservable = viewStateIntentObservable
                .mergeWith(intentObservable
                        .map { SplashViewState.afterTransition() }
                        .delay(1, TimeUnit.SECONDS))
                .mergeWith(Observable
                        .timer(3, TimeUnit.SECONDS)
                        .flatMapSingle { authService.isUserLoggedIn() }
                        .applyIoSchedulers()
                        .doOnNext(this::performNavigation)
                        .map { SplashViewState.afterTransition() })
    }

    private fun performNavigation(isUserLoggedIn: Boolean) =
            if (isUserLoggedIn) navigator.showHomeScreen()
            else navigator.showAuthScreen()
}