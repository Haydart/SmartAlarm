package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.Contracts

class AuthViewState private constructor(
        emailInputText: String = "",
        continueButtonEnabled: String = "",
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

sealed class ChangeAction {
    class ActionButtonClicked : ChangeAction()
    class ForgotPasswordClicked : ChangeAction()
    class EmailInput : ChangeAction()
    class PasswordInput : ChangeAction()
    class RepeatPasswordInput : ChangeAction()
}
