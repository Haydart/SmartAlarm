package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthViewStateChange.*

object AuthStateReducer {
    fun reduce(currentState: AuthViewState, change: AuthViewStateChange) = when (change) {
        is EmailInput -> {
            currentState.copy(emailInputText = change.email)
        }
        is PasswordInput -> {
            currentState.copy(passwordInputText = change.password)
        }
        is RepeatPasswordInput -> {
            currentState.copy(repeatPasswordInputText = change.repeatedPassword)
        }
        is PerspectiveSwitch -> {
            currentState.copy(screenPerspective = if (currentState.screenPerspective == AuthPerspective.LOGIN)
                AuthPerspective.REGISTER
            else AuthPerspective.LOGIN)
        }
    }
}