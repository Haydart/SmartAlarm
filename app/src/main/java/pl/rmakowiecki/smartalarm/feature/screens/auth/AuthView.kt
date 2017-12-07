package pl.rmakowiecki.smartalarm.feature.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AuthView : Contracts.View {

    val facebookAuthIntent: Observable<Unit>
    val googleAuthIntent: Observable<Unit>
    val emailInputIntent: Observable<String>
    val passwordInputIntent: Observable<String>
    val repeatPasswordInputIntent: Observable<String>

    val loginIntent: Observable<LoginCredentials>
    val registerIntent: Observable<RegisterCredentials>
    val resetPasswordIntent: Observable<RemindPasswordCredentials>

    val emailRegistrationIntent: Observable<Unit>
    val backButtonIntent: Observable<Unit>
    val forgotPasswordIntent: Observable<Unit>

    fun render(authViewState: AuthViewState)
}