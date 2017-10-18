package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable

class AuthInteractor : Auth.Interactor {

    private val mergedIntentsStream: Observable<AuthAction> = Observable.empty()

    override val viewStateStream: Observable<AuthViewState> = mergedIntentsStream
            .scan(AuthViewState.createInitial(), ::reduce)

    override fun harnessFacebookAuthIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.FacebookSignInClick() }
        )
    }

    override fun harnessGoogleAuthIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.GoogleSignInClick() }
        )
    }

    override fun harnessEmailInputIntent(intentObservable: Observable<String>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.EmailInput() }
        )
    }

    override fun harnessPasswordInputIntent(intentObservable: Observable<String>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.PasswordInput() }
        )
    }

    override fun harnessRepeatPasswordInputIntent(intentObservable: Observable<String>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.RepeatPasswordInput() }
        )
    }

    override fun harnessCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.CredentialsSubmitButtonClick() }
        )
    }

    override fun harnessEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.EmailRegistrationClick() }
        )
    }

    override fun harnessForgotPasswordIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthAction.ForgotPasswordClick() }
        )
    }
}

private fun reduce(initialState: AuthViewState, change: AuthAction): AuthViewState {
    return AuthViewState.createInitial()
}