package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Single

private const val EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}"
private const val PASSWORD_MINIMUM_LENGTH = 8

class CredentialsValidator {

    private var password: String = ""

    fun validateEmail(inputEmail: String): Single<Boolean> = Single
            .just(isValidEmail(inputEmail) || inputEmail.isBlank())

    private fun isValidEmail(string: String) =
            EMAIL_PATTERN.toRegex().matches(string)

    fun validatePassword(inputPassword: String): Single<Boolean> = Single
            .just(isValidPassword(inputPassword) || inputPassword.isBlank())
            .doOnSuccess { password = inputPassword }

    private fun isValidPassword(string: String) =
            string.trim().length >= PASSWORD_MINIMUM_LENGTH

    fun validatePasswordRepeat(inputRepeatPassword: String): Single<Boolean> = Single
            .just(isValidRepeatPassword(inputRepeatPassword) || inputRepeatPassword.isBlank())

    private fun isValidRepeatPassword(inputRepeatPassword: String) = inputRepeatPassword == password

    fun areLoginCredentialsValid(email: String, password: String) =
            isValidEmail(email) && isValidPassword(password)

    fun areRegisterCredentialsValid(email: String, password: String, repeatPassword: String) =
            isValidEmail(email) && isValidPassword(password) && isValidRepeatPassword(repeatPassword)

    fun areRemindPasswordCredentialsValid(inputEmail: String) = isValidEmail(inputEmail)
}