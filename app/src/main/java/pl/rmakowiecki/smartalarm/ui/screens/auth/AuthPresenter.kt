package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter(
        private val interactor: Auth.Interactor
) : MviPresenter<Auth.View, AuthViewState>() {

    override fun bindIntents() = with(interactor) {
        harnessFacebookAuthIntent(
                handleIntent(Auth.View::facebookAuthIntent))
        harnessGoogleAuthIntent(
                handleIntent(Auth.View::googleAuthIntent))
        harnessEmailInputIntent(
                handleIntent(Auth.View::emailInputIntent))
        harnessPasswordInputIntent(
                handleIntent(Auth.View::passwordInputIntent))
        harnessRepeatPasswordInputIntent(
                handleIntent(Auth.View::repeatPasswordInputIntent))
        harnessCredentialsSubmitIntent(
                handleIntent(Auth.View::credentialsSubmitIntent))
        harnessEmailRegistrationIntent(
                handleIntent(Auth.View::emailRegistrationIntent))
        harnessForgotPasswordIntent(
                handleIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(interactor.viewStateStream, Auth.View::render)
    }
}