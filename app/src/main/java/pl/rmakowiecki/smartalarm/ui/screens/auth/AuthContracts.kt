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
        val credentialsSubmitIntent: Observable<Credentials>
        val emailRegistrationIntent: Observable<Unit>
        val backButtonIntent: Observable<Unit>
        val forgotPasswordIntent: Observable<Unit>

        fun render(authViewState: AuthViewState)
    }

    interface Navigator : Contracts.Navigator

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<AuthViewState>

        fun attachFacebookAuthIntent(intentObservable: Observable<Unit>)
        fun attachGoogleAuthIntent(intentObservable: Observable<Unit>)
        fun attachEmailInputIntent(intentObservable: Observable<String>)
        fun attachPasswordInputIntent(intentObservable: Observable<String>)
        fun attachRepeatPasswordInputIntent(intentObservable: Observable<String>)
        fun attachCredentialsSubmitIntent(intentObservable: Observable<Credentials>)
        fun attachEmailRegistrationIntent(intentObservable: Observable<Unit>)
        fun attachBackButtonClickIntent(intentObservable: Observable<Unit>)
        fun attachForgotPasswordIntent(intentObservable: Observable<Unit>)
    }
}