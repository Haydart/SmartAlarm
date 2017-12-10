package pl.rmakowiecki.smartalarm.feature.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.domain.auth.LoginCredentials
import pl.rmakowiecki.smartalarm.domain.auth.RegisterCredentials
import pl.rmakowiecki.smartalarm.domain.auth.RemindPasswordCredentials

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