package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.Contracts

data class AuthViewState private constructor(
        val emailInputText: String = "",
        val emailInputError: String = "",
        val passwordInputText: String = "",
        val passwordInputError: String = "",
        val repeatPasswordInputText: String = "",
        val repeatPasswordInputError: String = "",
        val credentialsSubmitButtonEnabled: Boolean = false,
        val screenPerspective: AuthPerspective = AuthPerspective.LOGIN,
        val isLoading: Boolean = false,
        val generalError: String = ""
) : Contracts.ViewState {
    companion object {
        fun createInitial() = AuthViewState()
    }
}

enum class AuthPerspective {
    LOGIN,
    REGISTER
}

sealed class AuthViewStateChange {
    class EmailInput(val email: String) : AuthViewStateChange()
    class PasswordInput(val password: String) : AuthViewStateChange()
    class RepeatPasswordInput(val repeatedPassword: String) : AuthViewStateChange()
    class PerspectiveSwitch : AuthViewStateChange()
    class CredentialsSubmit : AuthViewStateChange()
}

sealed class AuthUseCaseChange {
    class ForgotPasswordClick : AuthUseCaseChange()
}
