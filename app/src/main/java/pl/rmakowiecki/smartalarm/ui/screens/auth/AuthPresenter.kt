package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter : MviPresenter<AuthView, AuthViewState>(AuthViewState.Idle()) {
    override fun bindIntents() {//todo implement
    }
}