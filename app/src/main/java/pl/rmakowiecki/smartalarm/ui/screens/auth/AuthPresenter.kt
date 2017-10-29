package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AuthPresenter @Inject constructor(
        private val interactor: AuthInteractor
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

        attachLoginIntent(
                bindIntent(Auth.View::loginIntent))

        attachRegisterIntent(
                bindIntent(Auth.View::registerIntent))

        attachResetPasswordIntent(
                bindIntent(Auth.View::resetPasswordIntent))

        attachEmailRegistrationIntent(
                bindIntent(Auth.View::emailRegistrationIntent))

        attachBackButtonClickIntent(
                bindIntent(Auth.View::backButtonIntent))

        attachForgotPasswordIntent(
                bindIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(getViewStateObservable(), Auth.View::render)
    }
}