package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthPerspective.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthInteractor @Inject constructor(
        private val navigator: AuthNavigator,
        private val reducer: AuthStateReducer,
        private val validator: CredentialsValidator,
        private val authService: FirebaseAuthService
) : Auth.Interactor {

    private var viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty()

    private var needsToRevalidateInput = false

    override fun getViewStateObservable(): Observable<AuthViewState> = viewStateIntentsObservable
            .scan(AuthViewState.createInitial(), reducer::reduce)
            .flatMap(this::changeCredentialsSubmitButtonStateIfNeeded)

    private fun changeCredentialsSubmitButtonStateIfNeeded(authViewState: AuthViewState): Observable<AuthViewState> = Observable.just(authViewState)
            .mergeWith(
                    if (needsToRevalidateInput) Observable
                            .just(reducer.reduce(authViewState, AuthViewStateChange.CredentialsButtonChange(
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
            AuthViewStateChange.EmailValidation(if (isValid) "" else "Invalid email format")

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

    private fun mapToPasswordError(isValid: Boolean) = AuthViewStateChange.PasswordValidation(
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

    private fun mapToRepeatPasswordError(arePasswordsIdentical: Boolean) = AuthViewStateChange.RepeatPasswordValidation(
            if (arePasswordsIdentical) "" else "Passwords are not identical"
    )

    override fun attachLoginIntent(intentObservable: Observable<LoginCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.CredentialsSubmit() })
                .mergeWith(intentObservable
                        .flatMapSingle(authService::login)
                        .flatMap(this::applyBackendLoginResponse))
    }

    private fun applyBackendLoginResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                Observable.just(AuthViewStateChange.AuthSuccess())
                        .doOnNext { navigator.showHomeScreen() }
            } else Observable.just(
                    AuthViewStateChange.AuthFailure(response.error?.localizedMessage ?: "Unknown error"))

    override fun attachRegisterIntent(intentObservable: Observable<RegisterCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.CredentialsSubmit() })
                .mergeWith(intentObservable
                        .flatMapSingle(authService::register)
                        .flatMap(this::applyBackendLoginResponse))
    }

    override fun attachResetPasswordIntent(intentObservable: Observable<RemindPasswordCredentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.CredentialsSubmit() })
                .mergeWith(intentObservable
                        .delay(1, TimeUnit.SECONDS)
                        .flatMapSingle(authService::resetPassword)
                        .flatMap(this::applyBackendPasswordResetResponse))
    }

    private fun applyBackendPasswordResetResponse(response: AuthResponse): Observable<out AuthViewStateChange> =
            if (response.isSuccessful) {
                Observable.just(AuthViewStateChange.AuthSuccess())
                        .doOnNext { navigator.showResetPasswordCompleteDialog() }
            } else {
                Observable.just(AuthViewStateChange.AuthFailure(""))
                        .doOnNext { navigator.showFailureDialog(response.error?.localizedMessage ?: "Unknown password reset error.") }
            }

    override fun attachEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.PerspectiveSwitch(REGISTER) }
                        .doAfterNext { needsToRevalidateInput = true })
    }

    override fun attachBackButtonClickIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.PerspectiveSwitch(LOGIN) })
    }

    override fun attachForgotPasswordIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.PerspectiveSwitch(FORGOT_PASSWORD) }
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

