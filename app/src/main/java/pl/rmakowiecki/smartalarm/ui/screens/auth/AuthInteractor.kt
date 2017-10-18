package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable

class AuthInteractor : Auth.Interactor {

    private val viewStateIntentsObservable: Observable<AuthViewStateChange> = Observable.empty()

    private val useCaseChangesObservable: Observable<AuthUseCaseChange> = Observable.empty()

    override val viewStateStream: Observable<AuthViewState> = viewStateIntentsObservable
            .scan(AuthViewState.createInitial(), AuthStateReducer::reduce)

    override fun harnessFacebookAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun harnessGoogleAuthIntent(intentObservable: Observable<Unit>) {
        //todo implement
    }

    override fun harnessEmailInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.EmailInput(it) }
        )
    }

    override fun harnessPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PasswordInput(it) }
        )
    }

    override fun harnessRepeatPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.RepeatPasswordInput(it) }
        )
    }

    override fun harnessCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.CredentialsSubmit() }
        )
    }

    override fun harnessEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        viewStateIntentsObservable.mergeWith(
                intentObservable.map { AuthViewStateChange.PerspectiveSwitch() }
        )
    }

    override fun harnessForgotPasswordIntent(intentObservable: Observable<Unit>) {
        useCaseChangesObservable.mergeWith(
                intentObservable.map { AuthUseCaseChange.ForgotPasswordClick() }
        )
    }
}

