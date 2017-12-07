package pl.rmakowiecki.smartalarm.feature.screens.setup

import io.reactivex.Observable
import javax.inject.Inject

class SetupInteractor @Inject constructor() {

    private var viewStateChanges = Observable.empty<SetupViewState>()

    val viewStateObservable: Observable<SetupViewState>
        get() = viewStateChanges

    fun attachSsidInputIntent(intentObservable: Observable<String>) = mergeChanges(
            intentObservable.map { SetupViewState() }
    )

    fun attachNetworkPasswordInputIntent(intentObservable: Observable<String>) = mergeChanges(
            intentObservable.map { SetupViewState() }
    )

    fun attachNetworkCredentialsSubmitIntent(intentObservable: Observable<Unit>) = mergeChanges(
            intentObservable.map { SetupViewState() }
    )

    private fun mergeChanges(vararg changes: Observable<out SetupViewState>) = changes.forEach {
        viewStateChanges = viewStateChanges.mergeWith(it)
    }

}