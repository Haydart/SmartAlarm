package pl.rmakowiecki.smartalarm.feature.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AuthPresenter @Inject constructor(
        private val interactor: AuthInteractor
) : MviPresenter<AuthView, AuthViewState>() {

    override fun bindIntents() = with(interactor) {
        attachFacebookAuthIntent(
                bindIntent(AuthView::facebookAuthIntent))

        attachGoogleAuthIntent(
                bindIntent(AuthView::googleAuthIntent))

        attachEmailInputIntent(
                bindIntent(AuthView::emailInputIntent))

        attachPasswordInputIntent(
                bindIntent(AuthView::passwordInputIntent))

        attachRepeatPasswordInputIntent(
                bindIntent(AuthView::repeatPasswordInputIntent))

        attachLoginIntent(
                bindIntent(AuthView::loginIntent))

        attachRegisterIntent(
                bindIntent(AuthView::registerIntent))

        attachResetPasswordIntent(
                bindIntent(AuthView::resetPasswordIntent))

        attachEmailRegistrationIntent(
                bindIntent(AuthView::emailRegistrationIntent))

        attachBackButtonClickIntent(
                bindIntent(AuthView::backButtonIntent))

        attachForgotPasswordIntent(
                bindIntent(AuthView::forgotPasswordIntent))

        subscribeViewState(getViewStateObservable(), AuthView::render)
    }
}