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
            if (!currentState.isLoading)
                currentState.copy(isLoading = true)
            else currentState
        }
        is PerspectiveSwitch -> {
            if (!currentState.isLoading)
                currentState.copy(screenPerspective = change.authPerspective)
            else currentState
        }
        is AuthViewStateChange.CredentialsButtonChange -> {
            currentState.copy(credentialsSubmitButtonEnabled = change.credentialsSubmitButtonEnabled)
        }
        is AuthViewStateChange.AuthSuccess -> {
            currentState.copy(isLoading = false, isShowingSuccess = true)
        }
        is AuthViewStateChange.AuthFailure -> {
            currentState.copy(isLoading = false, generalError = change.errorMessage)
        }
    }
}