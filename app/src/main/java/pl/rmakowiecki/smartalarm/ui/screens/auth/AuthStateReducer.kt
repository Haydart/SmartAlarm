package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthViewStateChange.*

class AuthStateReducer {
    fun reduce(currentState: AuthViewState, change: AuthViewStateChange) = when (change) {
        is EmailInput -> {
            currentState.copy(emailInputText = change.email, emailInputError = "")
        }
        is EmailValidation -> {
            currentState.copy(emailInputError = change.emailError)
        }
        is PasswordInput -> {
            currentState.copy(passwordInputText = change.password, passwordInputError = "")
        }
        is PasswordValidation -> {
            currentState.copy(passwordInputError = change.passwordError)
        }
        is RepeatPasswordInput -> {
            currentState.copy(repeatPasswordInputText = change.repeatedPassword)
        }
        is RepeatPasswordValidation -> {
            currentState.copy(repeatPasswordInputError = change.repeatedPasswordError)
        }
        is CredentialsSubmit -> {
            currentState.copy(isLoading = true)
        }
        is PerspectiveSwitch -> {
            currentState.copy(screenPerspective = change.authPerspective)
        }
    }
}