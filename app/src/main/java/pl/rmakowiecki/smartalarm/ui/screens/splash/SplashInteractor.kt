package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.extensions.applyIoSchedulers
import pl.rmakowiecki.smartalarm.ui.screens.auth.FirebaseAuthService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
                        .delay(1, TimeUnit.SECONDS))
                .mergeWith(Observable
                        .timer(3, TimeUnit.SECONDS)
                        .flatMapSingle { authService.isUserLoggedIn() }
                        .applyIoSchedulers()
                        .doOnNext(this::navigateToProperScreen)
                        .map { SplashViewState.afterTransition() })
    }

    private fun navigateToProperScreen(isUserLoggedIn: Boolean) =
            if (isUserLoggedIn) navigator.showHomeScreen()
            else navigator.showAuthScreen()
}