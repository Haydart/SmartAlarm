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
                bindIntent(Auth.View::loginIntent).doOnEach { logD("login intent presenter") })

        attachRegisterIntent(
                bindIntent(Auth.View::registerIntent).doOnEach { logD("register intent presenter") })

        attachResetPasswordIntent(
                bindIntent(Auth.View::resetPasswordIntent).doOnEach { logD("password reset intent presenter") })

        attachEmailRegistrationIntent(
                bindIntent(Auth.View::emailRegistrationIntent))

        attachBackButtonClickIntent(
                bindIntent(Auth.View::backButtonIntent))

        attachForgotPasswordIntent(
                bindIntent(Auth.View::forgotPasswordIntent))

        subscribeViewState(getViewStateObservable(), Auth.View::render)
    }
}