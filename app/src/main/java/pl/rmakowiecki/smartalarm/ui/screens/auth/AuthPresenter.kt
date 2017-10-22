package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import pl.rmakowiecki.smartalarm.extensions.logD

class AuthPresenter(
        private val interactor: Auth.Interactor
) : MviPresenter<Auth.View, AuthViewState>() {

    override fun bindIntents() = with(interactor) {
        attachFacebookAuthIntent(
                bindIntent(Auth.View::facebookAuthIntent))

        attachGoogleAuthIntent(
                bindIntent(Auth.View::googleAuthIntent))

        attachEmailInputIntent(
                bindIntent(Auth.View::emailInputIntent))

        attachPasswordInputIntent(
                bindIntent(Auth.View::passwordInputIntent))

        attachRepeatPasswordInputIntent(
                bindIntent(Auth.View::repeatPasswordInputIntent))

        attachRegisterIntent(
                bindIntent(Auth.View::loginIntent))

        attachEmailRegistrationIntent(
                bindIntent(Auth.View::emailRegistrationIntent).doOnEach { logD("email register click presenter") })

        attachBackButtonClickIntent(
                bindIntent(Auth.View::backButtonIntent).doOnEach { logD("back button click presenter") })

        attachForgotPasswordIntent(
                bindIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(getViewStateObservable(), Auth.View::render)
    }
}