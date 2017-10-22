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

        attachLoginIntent(
                bindIntent(Auth.View::loginIntent))

        attachRegisterIntent(
                bindIntent(Auth.View::registerIntent))

        attachResetPasswordIntent(
                bindIntent(Auth.View::resetPasswordIntent))

        attachEmailRegistrationIntent(
                bindIntent(Auth.View::emailRegistrationIntent).doOnEach { logD("email register click presenter") })

        attachBackButtonClickIntent(
                bindIntent(Auth.View::backButtonIntent).doOnEach { logD("back button click presenter") })

        attachForgotPasswordIntent(
                bindIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(getViewStateObservable(), Auth.View::render)
    }
}