package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Auth {
    interface View : Contracts.View {
        fun facebookAuthIntent(): Observable<Unit>
        fun googleAuthIntent(): Observable<Unit>
        fun emailInputIntent(): Observable<String>
        fun emailSubmitIntent(): Observable<Unit>
        fun forgotPasswordIntent(): Observable<Unit>

        fun render(authViewState: AuthViewState)
    }

    interface Navigator : Contracts.Navigator

    interface Interactor : Contracts.Interactor {
        fun harnessUserIntents(
                facebookButtonIntentObservable: Observable<Unit>,
                googleButtonIntentObservable: Observable<Unit>,
                emailInputIntentObservable: Observable<String>,
                continueButtonIntentObservable: Observable<Unit>,
                forgotPasswordIntentObservable: Observable<Unit>
        ): Pair<Observable<AuthViewState>, Observable<Unit>>
    }
}
