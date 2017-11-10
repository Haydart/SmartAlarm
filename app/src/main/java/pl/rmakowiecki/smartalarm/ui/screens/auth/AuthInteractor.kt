package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import io.reactivex.Single
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthPerspective.*
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthViewStateChange.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthInteractor @Inject constructor(
        private val navigator: AuthNavigator,
        private val reducer: AuthStateReducer,
        private val validator: CredentialsValidator,
        private val authService: FirebaseAuthService,
        private val setupService: FirebaseSetupService
) : Auth.Interactor {

    private var viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty()

    private var needsToRevalidateInput = false

    override fun getViewStateObservable(): Observable<AuthViewState> = viewStateIntentsObservable
            .scan(AuthViewState.createInitial(), reducer::reduce)
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

    override fun attachFacebookAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun attachGoogleAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun attachEmailInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map(AuthViewStateChange::EmailInput)
                        .doOnEach { needsToRevalidateInput = true })
                .mergeWith(intentObservable
                        .switchMapSingle(validator::validateEmail)
                        .map(this::mapToEmailError)
                        .delayInputValidation())
    }

    private fun Observable<out AuthViewStateChange>.delayInputValidation() = this.debounce(1, TimeUnit.SECONDS)

    private fun mapToEmailError(isValid: Boolean) =
            EmailValidation(if (isValid) "" else "Invalid email format")

    override fun attachPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map(AuthViewStateChange::PasswordInput)
                        .doOnEach { needsToRevalidateInput = true })
                .mergeWith(intentObservable
                        .switchMapSingle(validator::validatePassword)
                        .map(this::mapToPasswordError)
                        .delayInputValidation())
    }

    private fun mapToPasswordError(isValid: Boolean) = PasswordValidation(
            if (isValid) "" else "Password must be at least 8 characters long"
    )

    override fun attachRepeatPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map(AuthViewStateChange::RepeatPasswordInput)
                        .doOnEach { needsToRevalidateInput = true })
                .mergeWith(intentObservable
                        .switchMapSingle(validator::validatePasswordRepeat)
                        .map(this::mapToRepeatPasswordError)
                        .delayInputValidation())
    }

    private fun mapToRepeatPasswordError(arePasswordsIdentical: Boolean) = RepeatPasswordValidation(
            if (arePasswordsIdentical) "" else "Passwords are not identical"
    )

    override fun attachLoginIntent(intentObservable: Observable<LoginCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { CredentialsSubmit() })
                .mergeWith(intentObservable
                        .flatMapSingle(authService::login)
                        .flatMap(this::applyBackendAuthResponse))
    }

    private fun applyBackendAuthResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                setupService.fetchCoreDeviceUid()
                        .flatMap(this::validateAssignedCoreDeviceUid)
                        .toObservable()
                        .doOnNext(this::navigateToProperScreen)
                        .map { AuthSuccess() }
            } else Observable.just(
                    AuthFailure(response.error?.localizedMessage ?: "Unknown error"))

    private fun validateAssignedCoreDeviceUid(assignedCoreDeviceUiud: String) =
            if (assignedCoreDeviceUiud.isNotBlank()) {
                setupService.checkIfCoreDeviceExists(assignedCoreDeviceUiud)
            } else {
                Single.just(false)
            }

    private fun navigateToProperScreen(isSetupWithCoreDevice: Boolean) =
            if (isSetupWithCoreDevice) navigator.showHomeScreen()
            else navigator.showSetupScreen()

    override fun attachRegisterIntent(intentObservable: Observable<RegisterCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { CredentialsSubmit() })
                .mergeWith(intentObservable
                        .flatMapSingle(authService::register)
                        .flatMap(this::applyBackendAuthResponse))
    }

    override fun attachResetPasswordIntent(intentObservable: Observable<RemindPasswordCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { CredentialsSubmit() })
                .mergeWith(intentObservable
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

    override fun attachEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { PerspectiveSwitch(REGISTER) }
                        .doAfterNext { needsToRevalidateInput = true })
    }

    override fun attachBackButtonClickIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { PerspectiveSwitch(LOGIN) })
    }

    override fun attachForgotPasswordIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
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

