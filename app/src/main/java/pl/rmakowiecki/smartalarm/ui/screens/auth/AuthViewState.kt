package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.Contracts

class AuthViewState private constructor(
        emailInputText: String = "",
        continueButtonEnabled: Boolean = false,
        screenPerspective: AuthPerspective = AuthPerspective.LOGIN,
        isLoading: Boolean = false
) : Contracts.ViewState {
    companion object {
        fun createInitial() = AuthViewState()
    }
}

enum class AuthPerspective {
    LOGIN,
    REGISTER
}

sealed class AuthAction {
    class FacebookSignInClick : AuthAction()
    class GoogleSignInClick : AuthAction()
    class EmailInput : AuthAction()
    class PasswordInput : AuthAction()
    class RepeatPasswordInput : AuthAction()
    class CredentialsSubmitButtonClick : AuthAction()
    class EmailRegistrationClick : AuthAction()
    class ForgotPasswordClick : AuthAction()
}
