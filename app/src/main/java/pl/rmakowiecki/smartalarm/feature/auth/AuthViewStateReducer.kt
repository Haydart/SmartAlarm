package pl.rmakowiecki.smartalarm.feature.auth

import pl.rmakowiecki.smartalarm.domain.auth.AuthMode
import pl.rmakowiecki.smartalarm.feature.auth.AuthViewStateChange.*
import javax.inject.Inject

class AuthViewStateReducer @Inject constructor() {

    fun reduce(currentState: AuthViewState, change: AuthViewStateChange) = when (change) {

        is EmailInput -> currentState.copy(
                emailInputText = change.email,
                emailInputError = null
        )
        is PasswordInput -> currentState.copy(
                passwordInputText = change.password,
                passwordInputError = null
        )
        is RepeatPasswordInput -> currentState.copy(
                repeatPasswordInputText = change.repeatedPassword
        )
        is RepeatPasswordValidation -> currentState.copy(
                repeatPasswordInputError = change.repeatedPasswordError
        )
        is CredentialsButtonChange -> currentState.copy(
                credentialsSubmitButtonEnabled = change.credentialsSubmitButtonEnabled
        )
        is AuthSuccess -> currentState.copy(
                isLoading = false,
                isShowingSuccess = true
        )
        is AuthFailure -> currentState.copy(
                isLoading = false,
                generalError = change.errorMessage
        )
        is Neutral -> currentState.copy(
                isLoading = false,
                isShowingSuccess = false,
                generalError = null
        )
        is EmailValidation -> {
            if (currentState.screenPerspective != AuthMode.LOGIN)
                currentState.copy(emailInputError = change.emailError)
            else currentState
        }
        is PasswordValidation -> {
            if (currentState.screenPerspective != AuthMode.LOGIN)
                currentState.copy(passwordInputError = change.passwordError)
            else currentState
        }
        is CredentialsSubmit -> {
            if (!currentState.isLoading)
                currentState.copy(isLoading = true)
            else currentState
        }
        is PerspectiveSwitch -> {
            if (!currentState.isLoading)
                currentState.copy(screenPerspective = change.authPerspective, generalError = null,
                        emailInputError = null, passwordInputError = null, repeatPasswordInputError = null)
            else currentState
        }
    }
}