package pl.rmakowiecki.smartalarm.ui.screens.auth

private const val EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}"
private const val PASSWORD_MINIMUM_LENGTH = 8

class CredentialsValidator {

    fun isValidEmail(string: String) = EMAIL_PATTERN.toRegex().matches(string)

    fun isValidPassword(string: String) = string.trim().length >= PASSWORD_MINIMUM_LENGTH
}