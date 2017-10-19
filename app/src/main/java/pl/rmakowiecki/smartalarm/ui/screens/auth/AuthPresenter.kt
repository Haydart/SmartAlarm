package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

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
        attachCredentialsSubmitIntent(
                bindIntent(Auth.View::credentialsSubmitIntent))
        attachEmailRegistrationIntent(
                bindIntent(Auth.View::emailRegistrationIntent))
        attachForgotPasswordIntent(
                bindIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(interactor.getViewStateObservable(), Auth.View::render)
    }
}