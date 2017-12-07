package pl.rmakowiecki.smartalarm.feature.screens.main

import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor() {

    private var viewStateChanges = Observable.empty<HomeViewState>()

    val viewStateObservable: Observable<HomeViewState>
        get() = viewStateChanges

    fun attachTabSwitchIntent(intentObservable: Observable<Int>) = mergeChanges(
            intentObservable.map(::HomeViewState)
    )

    private fun mergeChanges(vararg changes: Observable<out HomeViewState>) = changes.forEach {
        viewStateChanges = viewStateChanges.mergeWith(it)
    }
}