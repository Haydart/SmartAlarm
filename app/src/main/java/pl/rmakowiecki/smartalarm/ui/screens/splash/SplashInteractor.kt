package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.extensions.applyIoSchedulers
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TRANSITION_DELAY = 1L
private const val NAVIGATION_DELAY = 3L

class SplashInteractor @Inject constructor(
        private val authService: FirebaseAuthService,
        private val navigator: SplashNavigator
) : Splash.Interactor {

    override var viewStateIntentObservable: Observable<SplashViewState> = Observable.empty()
        private set

    override fun attachTransitionIntent(intentObservable: Observable<Unit>) {
        viewStateIntentObservable = viewStateIntentObservable
                .mergeWith(intentObservable
                        .map { SplashViewState.afterTransition() }
                        .delay(TRANSITION_DELAY, TimeUnit.SECONDS))
                .mergeWith(Observable
                        .timer(NAVIGATION_DELAY, TimeUnit.SECONDS)
                        .flatMapSingle { authService.isUserLoggedIn() }
                        .applyIoSchedulers()
                        .doOnNext(this::performNavigation)
                        .map { SplashViewState.afterTransition() })
    }

    private fun performNavigation(isUserLoggedIn: Boolean) =
            if (isUserLoggedIn) navigator.showHomeScreen()
            else navigator.showAuthScreen()
}