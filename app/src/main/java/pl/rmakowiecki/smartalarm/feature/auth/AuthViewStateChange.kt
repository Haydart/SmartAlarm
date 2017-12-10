package pl.rmakowiecki.smartalarm.feature.auth

import pl.rmakowiecki.smartalarm.domain.auth.AuthMode

sealed class AuthViewStateChange {

    class EmailInput(val email: String) : AuthViewStateChange()
    class EmailValidation(val emailError: String) : AuthViewStateChange()
    class PasswordInput(val password: String) : AuthViewStateChange()
    class PasswordValidation(val passwordError: String) : AuthViewStateChange()
    class RepeatPasswordInput(val repeatedPassword: String) : AuthViewStateChange()
    class RepeatPasswordValidation(val repeatedPasswordError: String) : AuthViewStateChange()
    class PerspectiveSwitch(val authPerspective: AuthMode) : AuthViewStateChange()
    class CredentialsButtonChange(val credentialsSubmitButtonEnabled: Boolean) : AuthViewStateChange()
    object CredentialsSubmit : AuthViewStateChange()
    object AuthSuccess : AuthViewStateChange()
    class AuthFailure(val errorMessage: String) : AuthViewStateChange()
    object Neutral : AuthViewStateChange()
}