package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AuthView : Contracts.View {
    fun render(authViewState: AuthViewState)

    fun emailInputIntent(): Observable<String>
    fun emailSignInIntent(): Observable<Unit>
    fun googleAuthIntent(): Observable<Unit>
    fun facebookAuthIntent(): Observable<Unit>
}

sealed class AuthViewState : Contracts.ViewState {
    class Idle : AuthViewState()
    class Loading : AuthViewState()
}