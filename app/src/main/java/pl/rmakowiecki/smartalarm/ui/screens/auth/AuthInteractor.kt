package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class AuthInteractor(
        private val navigator: Auth.Navigator,
        private val reducer: AuthStateReducer,
        private val validator: CredentialsValidator
) : Auth.Interactor {

    private var viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty<AuthViewStateChange>()

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
            AuthPerspective.LOGIN -> validator
                    .areLoginCredentialsValid(emailInputText, passwordInputText)

            AuthPerspective.REGISTER -> validator
                    .areRegisterCredentialsValid(emailInputText, passwordInputText, repeatPasswordInputText)
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

    override fun attachCredentialsSubmitIntent(intentObservable: Observable<Credentials>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.CredentialsSubmit() }
        )
    }

    override fun attachEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PerspectiveSwitch(AuthPerspective.REGISTER) }
        )
    }

    override fun attachBackButtonClickIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PerspectiveSwitch(AuthPerspective.LOGIN) }
        )
    }

    override fun attachForgotPasswordIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }
}

data class Credentials(
        val email: String,
        val password: String,
        val repeatPassword: String
)

