package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class AuthInteractor(
        private val navigator: Auth.Navigator,
        private val reducer: AuthStateReducer,
        private val validator: CredentialsValidator
) : Auth.Interactor {

    private var viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty()

    override fun getViewStateObservable(): Observable<AuthViewState> = viewStateIntentsObservable
            .scan(AuthViewState.createInitial(), reducer::reduce)

    override fun attachFacebookAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun attachGoogleAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun attachEmailInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable
                        .map { AuthViewStateChange.EmailInput(it) })
                .mergeWith(intentObservable
                        .switchMapSingle(this::validateEmail)
                        .debounce(1, TimeUnit.SECONDS))
    }

    private fun validateEmail(inputEmail: String): Single<AuthViewStateChange.EmailValidation> = Single.just(
            AuthViewStateChange.EmailValidation(
                    if (validator.isValidEmail(inputEmail) || inputEmail.isBlank()) "" else "Invalid email"
            ))

    override fun attachPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(
                        intentObservable.map { AuthViewStateChange.PasswordInput(it) })
                .mergeWith(intentObservable
                        .switchMapSingle(this::validatePassword)
                        .debounce(1, TimeUnit.SECONDS))
    }

    private fun validatePassword(inputPassword: String): Single<AuthViewStateChange.PasswordValidation> = Single.just(
            AuthViewStateChange.PasswordValidation(
                    if (validator.isValidPassword(inputPassword) || inputPassword.isBlank()) "" else "Invalid password"
            ))

    override fun attachRepeatPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.RepeatPasswordInput(it) }
        )
    }

    override fun attachCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.CredentialsSubmit() }
        )
    }

    override fun attachEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PerspectiveSwitch() }
        )
    }

    override fun attachForgotPasswordIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }
}

