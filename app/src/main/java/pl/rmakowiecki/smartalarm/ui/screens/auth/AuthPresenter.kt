package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter(
        private val interactor: Auth.Interactor
) : MviPresenter<Auth.View, AuthViewState>() {

    override fun bindIntents() = with(interactor) {
        harnessFacebookAuthIntent(
                bindIntent(Auth.View::facebookAuthIntent))
        harnessGoogleAuthIntent(
                bindIntent(Auth.View::googleAuthIntent))
        harnessEmailInputIntent(
                bindIntent(Auth.View::emailInputIntent))
        harnessPasswordInputIntent(
                bindIntent(Auth.View::passwordInputIntent))
        harnessRepeatPasswordInputIntent(
                bindIntent(Auth.View::repeatPasswordInputIntent))
        harnessCredentialsSubmitIntent(
                bindIntent(Auth.View::credentialsSubmitIntent))
        harnessEmailRegistrationIntent(
                bindIntent(Auth.View::emailRegistrationIntent))
        harnessForgotPasswordIntent(
                bindIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(interactor.getViewStateObservable(), Auth.View::render)
    }
}