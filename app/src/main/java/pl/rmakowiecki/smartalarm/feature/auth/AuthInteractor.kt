package pl.rmakowiecki.smartalarm.feature.auth

import io.reactivex.Observable
import io.reactivex.Single
import pl.rmakowiecki.smartalarm.data.auth.AuthResponse
import pl.rmakowiecki.smartalarm.data.auth.FirebaseAuthService
import pl.rmakowiecki.smartalarm.data.auth.FirebaseSetupService
import pl.rmakowiecki.smartalarm.domain.auth.AuthMode.*
import pl.rmakowiecki.smartalarm.domain.auth.CredentialsValidator
import pl.rmakowiecki.smartalarm.domain.auth.LoginCredentials
import pl.rmakowiecki.smartalarm.domain.auth.RegisterCredentials
import pl.rmakowiecki.smartalarm.domain.auth.ResetPasswordIntent
import pl.rmakowiecki.smartalarm.feature.auth.AuthViewStateChange.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthInteractor @Inject constructor(
        private val navigator: AuthNavigator,
        private val reducer: AuthViewStateReducer,
        private val validator: CredentialsValidator,
        private val authService: FirebaseAuthService,
        private val setupService: FirebaseSetupService
) {

    private var viewStateChanges: Observable<AuthViewStateChange> = Observable.empty()

    private var needsToRevalidateInput = false

    val viewStateObservable: Observable<AuthViewState>
        get() = viewStateChanges
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

    fun attachFacebookAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    fun attachGoogleAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    fun attachEmailInputIntent(intentObservable: Observable<String>) = mergeChanges(
            intentObservable
                    .map(AuthViewStateChange::EmailInput)
                    .doOnEach { needsToRevalidateInput = true },
            intentObservable
                    .switchMapSingle(validator::validateEmail)
                    .map(this::mapToEmailError)
                    .delayInputValidation()
    )

    private fun Observable<out AuthViewStateChange>.delayInputValidation() = this.debounce(1, TimeUnit.SECONDS)

    private fun mapToEmailError(isValid: Boolean) =
            EmailValidation(if (isValid) "" else "Invalid email format")

    fun attachPasswordInputIntent(intentObservable: Observable<String>) = mergeChanges(
            intentObservable
                    .map(AuthViewStateChange::PasswordInput)
                    .doOnEach { needsToRevalidateInput = true },
            intentObservable
                    .switchMapSingle(validator::validatePassword)
                    .map(this::mapToPasswordError)
                    .delayInputValidation()
    )

    private fun mapToPasswordError(isValid: Boolean) = PasswordValidation(
            if (isValid) "" else "Password must be at least 8 characters long"
    )

    fun attachRepeatPasswordInputIntent(intentObservable: Observable<String>) = mergeChanges(
            intentObservable
                    .map(AuthViewStateChange::RepeatPasswordInput)
                    .doOnEach { needsToRevalidateInput = true },
            intentObservable
                    .switchMapSingle(validator::validatePasswordRepeat)
                    .map(this::mapToRepeatPasswordError)
                    .delayInputValidation()
    )

    private fun mapToRepeatPasswordError(arePasswordsIdentical: Boolean) = RepeatPasswordValidation(
            if (arePasswordsIdentical) "" else "Passwords are not identical"
    )

    fun attachLoginIntent(intentObservable: Observable<LoginCredentials>) = mergeChanges(
            intentObservable
                    .map { CredentialsSubmit },
            intentObservable
                    .flatMapSingle(authService::login)
                    .flatMap(this::applyBackendAuthResponse)
    )

    private fun applyBackendAuthResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                setupService.fetchUsersCoreDeviceUid()
                        .flatMap(this::validateAssignedCoreDeviceUid)
                        .toObservable()
                        .doOnNext(this::navigateToProperScreen)
                        .map { AuthSuccess }
            } else Observable.just(
                    AuthFailure(response.error?.localizedMessage ?: "Unknown error"))

    private fun validateAssignedCoreDeviceUid(assignedCoreDeviceUiud: String) =
            if (assignedCoreDeviceUiud.isNotBlank())
                setupService.checkIfCoreDeviceExists(assignedCoreDeviceUiud)
            else Single.just(false)

    private fun navigateToProperScreen(isSetupWithCoreDevice: Boolean) =
            if (isSetupWithCoreDevice) navigator.showHomeScreen()
            else navigator.showSetupScreen()

    fun attachRegisterIntent(intentObservable: Observable<RegisterCredentials>) = mergeChanges(
            intentObservable
                    .map { CredentialsSubmit },
            intentObservable
                    .flatMapSingle(authService::register)
                    .flatMap(this::applyBackendAuthResponse)
    )

    fun attachResetPasswordIntent(intentObservable: Observable<ResetPasswordIntent>) = mergeChanges(
            intentObservable
                    .map { CredentialsSubmit },
            intentObservable
                    .delay(1, TimeUnit.SECONDS)
                    .flatMapSingle(authService::resetPassword)
                    .flatMap(this::applyBackendPasswordResetResponse)
    )

    fun attachEmailRegistrationIntent(intentObservable: Observable<Unit>) = mergeChanges(
            intentObservable
                    .map { PerspectiveSwitch(REGISTER) }
                    .doAfterNext { needsToRevalidateInput = true }
    )

    fun attachBackButtonClickIntent(intentObservable: Observable<Unit>) = mergeChanges(
            intentObservable.map { PerspectiveSwitch(LOGIN) }
    )

    fun attachForgotPasswordIntent(intentObservable: Observable<Unit>) = mergeChanges(
            intentObservable
                    .map { PerspectiveSwitch(FORGOT_PASSWORD) }
                    .doAfterNext { needsToRevalidateInput = true }
    )

    private fun applyBackendPasswordResetResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                Observable.just(AuthSuccess)
                        .doOnNext { navigator.showResetPasswordCompleteDialog() }
            } else {
                Observable.just(AuthFailure(""))
                        .doOnNext { navigator.showFailureDialog(response.error?.localizedMessage ?: "Unknown password reset error.") }
            }

    private fun <T : AuthViewStateChange> mergeChanges(vararg changes: Observable<out T>) = changes.forEach {
        viewStateChanges = viewStateChanges.mergeWith(it)
    }
}