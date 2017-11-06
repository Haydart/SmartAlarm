package pl.rmakowiecki.smartalarm.ui.screens.splash

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts
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

    override var stubIntentObservable: Observable<Contracts.ViewState> = Observable.empty<Contracts.ViewState>()
            .mergeWith(Observable
                    .timer(TRANSITION_DELAY, TimeUnit.SECONDS)
                    .applyIoSchedulers()
                    .doOnNext { navigator.startLogoTransition() }
                    .flatMap { Observable.empty<Contracts.ViewState>() })
            .mergeWith(Observable
                    .timer(NAVIGATION_DELAY, TimeUnit.SECONDS)
                    .applyIoSchedulers()
                    .flatMapSingle { authService.isUserLoggedIn() }
                    .doOnNext(this::performNavigation)
                    .flatMap { Observable.empty<Contracts.ViewState>() })
        private set

    private fun performNavigation(isUserLoggedIn: Boolean) =
            if (isUserLoggedIn) navigator.showHomeScreen()
            else navigator.showAuthScreen()
}