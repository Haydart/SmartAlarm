package pl.rmakowiecki.smartalarm.feature.screens.splash

import io.reactivex.Observable
import io.reactivex.Single
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.data.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.data.auth.FirebaseSetupService
import pl.rmakowiecki.smartalarm.domain.splash.UserState
import pl.rmakowiecki.smartalarm.extensions.applyIoSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TRANSITION_DELAY = 1L
private const val NAVIGATION_DELAY = 3L

class SplashInteractor @Inject constructor(
        private val authService: FirebaseAuthService,
        private val setupService: FirebaseSetupService,
        private val navigator: SplashNavigator
) {

    var stubintent: Observable<Contracts.ViewState> = Observable.empty<Contracts.ViewState>()
            .mergeWith(Observable
                    .timer(TRANSITION_DELAY, TimeUnit.SECONDS)
                    .applyIoSchedulers()
                    .doOnNext { navigator.startLogoTransition() }
                    .flatMap { Observable.empty<Contracts.ViewState>() })
            .mergeWith(Observable
                    .timer(NAVIGATION_DELAY, TimeUnit.SECONDS)
                    .applyIoSchedulers()
                    .flatMapSingle { authService.isUserLoggedIn() }
                    .map { UserState(it, false) }
                    .flatMapSingle { checkIfUserIsSetupWithCoreDevice(it.isLoggedIn) }
                    .doOnNext(this::navigateToProperScreen)
                    .flatMap { Observable.empty<Contracts.ViewState>() })
        private set

    private fun checkIfUserIsSetupWithCoreDevice(isUserLoggedIn: Boolean) =
            if (isUserLoggedIn) setupService.fetchUsersCoreDeviceUid()
                    .flatMap(setupService::checkIfCoreDeviceExists)
                    .map { UserState(isUserLoggedIn, it) }
            else Single.just(UserState(false, false))

    private fun navigateToProperScreen(determinedUserState: UserState) = with(determinedUserState) {
        if (isLoggedIn && isSetUpWithCoreDevice) {
            navigator.showHomeScreen()
        } else if (isLoggedIn && !isSetUpWithCoreDevice) {
            navigator.showSetupScreen()
        } else {
            navigator.showAuthScreen()
        }
    }
}