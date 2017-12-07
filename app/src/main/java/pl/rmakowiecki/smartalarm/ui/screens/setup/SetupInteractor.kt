package pl.rmakowiecki.smartalarm.ui.screens.setup

import io.reactivex.Observable
import javax.inject.Inject

class SetupInteractor @Inject constructor() {

    private var viewStateObservable = Observable.empty<SetupViewState>()

    fun getViewStateObservable(): Observable<SetupViewState> = viewStateObservable

    fun attachSsidInputIntent(intentObservable: Observable<String>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SetupViewState() }
        )
    }

    fun attachNetworkPasswordInputIntent(intentObservable: Observable<String>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SetupViewState() }
        )
    }

    fun attachNetworkCredentialsSubmitIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { SetupViewState() }
        )
    }
}