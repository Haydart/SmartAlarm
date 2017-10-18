package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Auth {
    interface View : Contracts.View {
        fun facebookAuthIntent(): Observable<Unit>
        fun googleAuthIntent(): Observable<Unit>
        fun emailInputIntent(): Observable<String>
        fun passwordInputIntent(): Observable<String>
        fun repeatPasswordInputIntent(): Observable<String>
        fun credentialsSubmitIntent(): Observable<Unit>
        fun emailRegistrationIntent(): Observable<Unit>
        fun forgotPasswordIntent(): Observable<Unit>

        fun render(authViewState: AuthViewState)
    }

    interface Navigator : Contracts.Navigator

    interface Interactor : Contracts.Interactor {
        val viewStateStream: Observable<AuthViewState>

        fun harnessFacebookAuthIntent(intentObservable: Observable<Unit>)
        fun harnessGoogleAuthIntent(intentObservable: Observable<Unit>)
        fun harnessEmailInputIntent(intentObservable: Observable<String>)
        fun harnessPasswordInputIntent(intentObservable: Observable<String>)
        fun harnessRepeatPasswordInputIntent(intentObservable: Observable<String>)
        fun harnessCredentialsSubmitIntent(intentObservable: Observable<Unit>)
        fun harnessEmailRegistrationIntent(intentObservable: Observable<Unit>)
        fun harnessForgotPasswordIntent(intentObservable: Observable<Unit>)
    }
}
