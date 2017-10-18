package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.Contracts

data class AuthViewState private constructor(
        val emailInputText: String = "",
        val passwordInputText: String = "",
        val repeatPasswordInputText: String = "",
        val continueButtonEnabled: Boolean = false,
        val screenPerspective: AuthPerspective = AuthPerspective.LOGIN,
        val isLoading: Boolean = false
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
}
