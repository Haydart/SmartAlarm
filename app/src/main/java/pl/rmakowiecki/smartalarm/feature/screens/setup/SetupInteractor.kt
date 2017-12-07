package pl.rmakowiecki.smartalarm.feature.screens.setup

import io.reactivex.Observable
import javax.inject.Inject

class SetupInteractor @Inject constructor() {

    private var viewStateChanges = Observable.empty<SetupViewState>()

    val viewStateObservable: Observable<SetupViewState>
        get() = viewStateChanges

    fun attachSsidInputIntent(intent: Observable<String>) = mergeChanges(
            intent.map { SetupViewState() }
    )

    fun attachNetworkPasswordInputIntent(intent: Observable<String>) = mergeChanges(
            intent.map { SetupViewState() }
    )

    fun attachNetworkCredentialsSubmitIntent(intent: Observable<Unit>) = mergeChanges(
            intent.map { SetupViewState() }
    )

    private fun mergeChanges(changes: Observable<SetupViewState>) {
        viewStateChanges = viewStateChanges.mergeWith(changes)
    }
}