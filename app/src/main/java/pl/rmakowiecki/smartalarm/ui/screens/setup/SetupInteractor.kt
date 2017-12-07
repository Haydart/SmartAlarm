package pl.rmakowiecki.smartalarm.ui.screens.setup

import io.reactivex.Observable
import javax.inject.Inject

class SetupInteractor @Inject constructor() {

    private var viewStateObservable = Observable.empty<SetupViewState>()

    fun getViewStateObservable(): Observable<SetupViewState> = viewStateObservable

    fun attachSsidInputIntent(intent: Observable<String>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intent.map { SetupViewState() }
        )
    }

    fun attachNetworkPasswordInputIntent(intent: Observable<String>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intent.map { SetupViewState() }
        )
    }

    fun attachNetworkCredentialsSubmitIntent(intent: Observable<Unit>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intent.map { SetupViewState() }
        )
    }
}