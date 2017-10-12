package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AuthView : Contracts.View {
    fun render(authViewState: AuthViewState)

    fun emailInputIntent(): Observable<String>
    fun emailSubmitIntent(): Observable<Unit>
    fun googleAuthIntent(): Observable<Unit>
    fun facebookAuthIntent(): Observable<Unit>
}

data class AuthViewState(
        val
) : Contracts.ViewState