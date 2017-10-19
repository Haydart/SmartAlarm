package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Auth {
    interface View : Contracts.View {
        val facebookAuthIntent: Observable<Unit>
        val googleAuthIntent: Observable<Unit>
        val emailInputIntent: Observable<String>
        val passwordInputIntent: Observable<String>
        val repeatPasswordInputIntent: Observable<String>
        val credentialsSubmitIntent: Observable<Unit>
        val emailRegistrationIntent: Observable<Unit>
        val forgotPasswordIntent: Observable<Unit>

        fun render(authViewState: AuthViewState)
    }

    interface Navigator : Contracts.Navigator

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<AuthViewState>

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