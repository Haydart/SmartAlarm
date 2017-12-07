package pl.rmakowiecki.smartalarm.feature.screens.auth

import io.reactivex.Observable
import io.reactivex.Single
import pl.rmakowiecki.smartalarm.data.auth.AuthResponse
import pl.rmakowiecki.smartalarm.data.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.data.auth.FirebaseSetupService
import pl.rmakowiecki.smartalarm.domain.auth.AuthMode.*
import pl.rmakowiecki.smartalarm.domain.auth.CredentialsValidator
import pl.rmakowiecki.smartalarm.feature.screens.auth.AuthViewStateChange.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthInteractor @Inject constructor(
        private val navigator: AuthNavigator,
        private val reducer: AuthStateReducer,
        private val validator: CredentialsValidator,
        private val authService: FirebaseAuthService,
        private val setupService: FirebaseSetupService
) {

    private var viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty()

    private var needsToRevalidateInput = false

    fun getViewStateObservable(): Observable<AuthViewState> = viewStateIntentsObservable
            .scan(AuthViewState(), reducer::reduce)
            .flatMap(this::changeCredentialsSubmitButtonStateIfNeeded)

    private fun changeCredentialsSubmitButtonStateIfNeeded(authViewState: AuthViewState): Observable<AuthViewState> = Observable.just(authViewState)
            .mergeWith(
                    if (needsToRevalidateInput) Observable
                            .just(reducer.reduce(authViewState, CredentialsButtonChange(
                                    isCredentialInputValid(authViewState)
                            )))
                    else Observable.empty<AuthViewState>()
            )

    private fun isCredentialInputValid(currentViewState: AuthViewState) = with(currentViewState) {
        when (currentViewState.screenPerspective) {
            LOGIN -> validator.areLoginCredentialsValid(emailInputText, passwordInputText)
            REGISTER -> validator.areRegisterCredentialsValid(emailInputText, passwordInputText, repeatPasswordInputText)
            FORGOT_PASSWORD -> validator.areRemindPasswordCredentialsValid(emailInputText)
        }
    }

    fun attachFacebookAuthIntent(intent: Observable<Unit>) {
        //todo implement
    }

    fun attachGoogleAuthIntent(intent: Observable<Unit>) {
        //todo implement
    }

    fun attachEmailInputIntent(intent: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map(AuthViewStateChange::EmailInput)
                        .doOnEach { needsToRevalidateInput = true })
                .mergeWith(intent
                        .switchMapSingle(validator::validateEmail)
                        .map(this::mapToEmailError)
                        .delayInputValidation())
    }

    private fun Observable<out AuthViewStateChange>.delayInputValidation() = this.debounce(1, TimeUnit.SECONDS)

    private fun mapToEmailError(isValid: Boolean) =
            EmailValidation(if (isValid) "" else "Invalid email format")

    fun attachPasswordInputIntent(intent: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map(AuthViewStateChange::PasswordInput)
                        .doOnEach { needsToRevalidateInput = true })
                .mergeWith(intent
                        .switchMapSingle(validator::validatePassword)
                        .map(this::mapToPasswordError)
                        .delayInputValidation())
    }

    private fun mapToPasswordError(isValid: Boolean) = PasswordValidation(
            if (isValid) "" else "Password must be at least 8 characters long"
    )

    fun attachRepeatPasswordInputIntent(intent: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map(AuthViewStateChange::RepeatPasswordInput)
                        .doOnEach { needsToRevalidateInput = true })
                .mergeWith(intent
                        .switchMapSingle(validator::validatePasswordRepeat)
                        .map(this::mapToRepeatPasswordError)
                        .delayInputValidation())
    }

    private fun mapToRepeatPasswordError(arePasswordsIdentical: Boolean) = RepeatPasswordValidation(
            if (arePasswordsIdentical) "" else "Passwords are not identical"
    )

    fun attachLoginIntent(intent: Observable<LoginCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map { CredentialsSubmit() })
                .mergeWith(intent
                        .flatMapSingle(authService::login)
                        .flatMap(this::applyBackendAuthResponse))
    }

    private fun applyBackendAuthResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                setupService.fetchUsersCoreDeviceUid()
                        .flatMap(this::validateAssignedCoreDeviceUid)
                        .toObservable()
                        .doOnNext(this::navigateToProperScreen)
                        .map { AuthSuccess() }
            } else Observable.just(
                    AuthFailure(response.error?.localizedMessage ?: "Unknown error"))

    private fun validateAssignedCoreDeviceUid(assignedCoreDeviceUiud: String) =
            if (assignedCoreDeviceUiud.isNotBlank())
                setupService.checkIfCoreDeviceExists(assignedCoreDeviceUiud)
            else Single.just(false)

    private fun navigateToProperScreen(isSetupWithCoreDevice: Boolean) =
            if (isSetupWithCoreDevice) navigator.showHomeScreen()
            else navigator.showSetupScreen()

    fun attachRegisterIntent(intent: Observable<RegisterCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map { CredentialsSubmit() })
                .mergeWith(intent
                        .flatMapSingle(authService::register)
                        .flatMap(this::applyBackendAuthResponse))
    }

    fun attachResetPasswordIntent(intent: Observable<RemindPasswordCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map { CredentialsSubmit() })
                .mergeWith(intent
                        .delay(1, TimeUnit.SECONDS)
                        .flatMapSingle(authService::resetPassword)
                        .flatMap(this::applyBackendPasswordResetResponse))
    }

    private fun applyBackendPasswordResetResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                Observable.just(AuthSuccess())
                        .doOnNext { navigator.showResetPasswordCompleteDialog() }
            } else {
                Observable.just(AuthFailure(""))
                        .doOnNext { navigator.showFailureDialog(response.error?.localizedMessage ?: "Unknown password reset error.") }
            }

    fun attachEmailRegistrationIntent(intent: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map { PerspectiveSwitch(REGISTER) }
                        .doAfterNext { needsToRevalidateInput = true })
    }

    fun attachBackButtonClickIntent(intent: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map { PerspectiveSwitch(LOGIN) })
    }

    fun attachForgotPasswordIntent(intent: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intent
                        .map { PerspectiveSwitch(FORGOT_PASSWORD) }
                        .doAfterNext { needsToRevalidateInput = true })
    }
}

data class LoginCredentials(
        val email: String,
        val password: String
)

data class RegisterCredentials(
        val email: String,
        val password: String,
        val repeatPassword: String
)

data class RemindPasswordCredentials(
        val email: String
)