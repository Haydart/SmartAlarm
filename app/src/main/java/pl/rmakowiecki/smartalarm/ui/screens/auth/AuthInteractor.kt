package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable

class AuthInteractor : Auth.Interactor {

    private val mergedIntentsStream: Observable<AuthViewStateChange> = Observable.empty()

    override val viewStateStream: Observable<AuthViewState> = mergedIntentsStream
            .scan(AuthViewState.createInitial(), AuthStateReducer::reduce)

    override fun harnessFacebookAuthIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.FacebookSignInClick() }
        )
    }

    override fun harnessGoogleAuthIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.GoogleSignInClick() }
        )
    }

    override fun harnessEmailInputIntent(intentObservable: Observable<String>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.EmailInput() }
        )
    }

    override fun harnessPasswordInputIntent(intentObservable: Observable<String>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.PasswordInput() }
        )
    }

    override fun harnessRepeatPasswordInputIntent(intentObservable: Observable<String>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.RepeatPasswordInput() }
        )
    }

    override fun harnessCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.CredentialsSubmitButtonClick() }
        )
    }

    override fun harnessEmailRegistrationIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.EmailRegistrationClick() }
        )
    }

    override fun harnessForgotPasswordIntent(intentObservable: Observable<Unit>) {
        mergedIntentsStream.mergeWith(
                intentObservable.map { AuthViewStateChange.ForgotPasswordClick() }
        )
    }
}