package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.Contracts

data class AuthViewState private constructor(
        val emailInputText: String = "",
        val emailInputError: String? = null,
        val passwordInputText: String = "",
        val passwordInputError: String? = null,
        val repeatPasswordInputText: String = "",
        val repeatPasswordInputError: String? = null,
        val credentialsSubmitButtonEnabled: Boolean = false,
        val screenPerspective: AuthPerspective = AuthPerspective.LOGIN,
        val isLoading: Boolean = false,
        val isShowingSuccess: Boolean = false,
        val generalError: String? = null
) : Contracts.ViewState {
    companion object {
        fun createInitial() = AuthViewState()
    }
}

enum class AuthPerspective {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD
}

sealed class AuthViewStateChange {
    class EmailInput(val email: String) : AuthViewStateChange()
    class EmailValidation(val emailError: String) : AuthViewStateChange()
    class PasswordInput(val password: String) : AuthViewStateChange()
    class PasswordValidation(val passwordError: String) : AuthViewStateChange()
    class RepeatPasswordInput(val repeatedPassword: String) : AuthViewStateChange()
    class RepeatPasswordValidation(val repeatedPasswordError: String) : AuthViewStateChange()
    class PerspectiveSwitch(val authPerspective: AuthPerspective) : AuthViewStateChange()
    class CredentialsButtonChange(val credentialsSubmitButtonEnabled: Boolean) : AuthViewStateChange()
    class CredentialsSubmit : AuthViewStateChange()
    class AuthSuccess : AuthViewStateChange()
    class AuthFailure(val errorMessage: String) : AuthViewStateChange()
    class Neutral : AuthViewStateChange()
}
