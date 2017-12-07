package pl.rmakowiecki.smartalarm.ui.screens.main

import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor() {

    private var viewStateIntentsObservable: Observable<HomeViewState> = Observable.empty()

    fun getViewStateObservable() = viewStateIntentsObservable

    fun attachTabSwitchIntent(intentObservable: Observable<Int>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map(::HomeViewState)
        )
    }
}