package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AuthInteractor : Auth.Interactor {

    override val mergedIntentStream: Observable<AuthViewState>
        get() = PublishSubject.create<AuthViewState>()

    override fun harnessUserIntents(
            facebookButtonIntentObservable: Observable<Unit>,
            googleButtonIntentObservable: Observable<Unit>,
            emailInputIntentObservable: Observable<String>,
            continueButtonIntentObservable: Observable<Unit>,
            forgotPasswordIntentObservable: Observable<Unit>) {
        //todo implement business logic
    }
}