package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable

class AuthInteractor : Auth.Interactor {

    override fun harnessUserIntents(
            facebookButtonIntentObservable: Observable<Unit>,
            googleButtonIntentObservable: Observable<Unit>,
            emailInputIntentObservable: Observable<String>,
            continueButtonIntentObservable: Observable<Unit>,
            forgotPasswordIntentObservable: Observable<Unit>): Pair<Observable<AuthViewState>, Observable<Unit>> {

        val viewStateStream = Observable.merge(
                emailInputIntentObservable.map { ChangeAction.EmailInput() },
                continueButtonIntentObservable.map { ChangeAction.ActionButtonClicked() }
        ).scan(AuthViewState.createInitial(), this::reducer)

        val useCaseEventStream = Observable.empty<Unit>()

        return Pair(viewStateStream, useCaseEventStream)
    }

    private fun reducer(initialState: AuthViewState, change: ChangeAction): AuthViewState {
        return AuthViewState.createInitial()
    }
}