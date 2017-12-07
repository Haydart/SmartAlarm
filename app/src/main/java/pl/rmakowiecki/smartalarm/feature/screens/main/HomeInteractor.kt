package pl.rmakowiecki.smartalarm.feature.screens.main

import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor() {

    private var viewStateChanges = Observable.empty<HomeViewState>()

    val viewStateObservable: Observable<HomeViewState>
        get() = viewStateChanges

    fun attachTabSwitchIntent(intent: Observable<Int>) = mergeChanges(
            intent.map(::HomeViewState)
    )

    private fun mergeChanges(changes: Observable<HomeViewState>) {
        viewStateChanges = viewStateChanges.mergeWith(changes)
    }
}