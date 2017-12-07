package pl.rmakowiecki.smartalarm.feature.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AuthPresenter @Inject constructor(
        private val interactor: AuthInteractor
) : MviPresenter<AuthView, AuthViewState>() {

    override fun bindIntents() = with(interactor) {
        attachFacebookAuthIntent(
                intent(AuthView::facebookAuthIntent)
        )

        attachGoogleAuthIntent(
                intent(AuthView::googleAuthIntent)
        )

        attachEmailInputIntent(
                intent(AuthView::emailInputIntent)
        )

        attachPasswordInputIntent(
                intent(AuthView::passwordInputIntent)
        )

        attachRepeatPasswordInputIntent(
                intent(AuthView::repeatPasswordInputIntent)
        )

        attachLoginIntent(
                intent(AuthView::loginIntent)
        )

        attachRegisterIntent(
                intent(AuthView::registerIntent)
        )

        attachResetPasswordIntent(
                intent(AuthView::resetPasswordIntent)
        )

        attachEmailRegistrationIntent(
                intent(AuthView::emailRegistrationIntent)
        )

        attachBackButtonClickIntent(
                intent(AuthView::backButtonIntent)
        )

        attachForgotPasswordIntent(
                intent(AuthView::forgotPasswordIntent)
        )

        subscribeViewState(viewStateObservable, AuthView::render)
    }
}