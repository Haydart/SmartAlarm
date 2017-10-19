package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.extensions.isValidEmail
import java.util.concurrent.TimeUnit

class AuthInteractor(
        private val navigator: Auth.Navigator,
        private val reducer: AuthStateReducer
) : Auth.Interactor {

    private var viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty()

    override fun getViewStateObservable(): Observable<AuthViewState> = viewStateIntentsObservable
            .scan(AuthViewState.createInitial(), reducer::reduce)

    override fun harnessFacebookAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun harnessGoogleAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun harnessEmailInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable
                .mergeWith(intentObservable.map { AuthViewStateChange.EmailInput(it) }.doOnEach { })
                .mergeWith(intentObservable.switchMap {
                    validateEmail(it).debounce(1, TimeUnit.SECONDS)
                })
    }

    private fun validateEmail(inputEmail: String): Observable<AuthViewStateChange.EmailValidation> = Observable.just(
            AuthViewStateChange.EmailValidation(
                    if (inputEmail.isValidEmail()) "" else "Invalid email"
            ))

    override fun harnessPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PasswordInput(it) }
        )
    }

    override fun harnessRepeatPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.RepeatPasswordInput(it) }
        )
    }

    override fun harnessCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.CredentialsSubmit() }
        )
    }

    override fun harnessEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PerspectiveSwitch() }
        )
    }

    override fun harnessForgotPasswordIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }
}

