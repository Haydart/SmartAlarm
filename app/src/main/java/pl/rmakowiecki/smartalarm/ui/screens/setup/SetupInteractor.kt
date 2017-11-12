package pl.rmakowiecki.smartalarm.ui.screens.setup

import io.reactivex.Observable
import javax.inject.Inject

class SetupInteractor @Inject constructor() : Setup.Interactor {

    private var viewStateObservable = Observable.empty<SetupViewState>()

    override fun getViewStateObservable(): Observable<SetupViewState> = viewStateObservable

    override fun attachSsidInputIntent(intentObservable: Observable<String>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SetupViewState() }
        )
    }

    override fun attachNetworkPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SetupViewState() }
        )
    }

    override fun attachNetworkCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SetupViewState() }
        )
    }
}