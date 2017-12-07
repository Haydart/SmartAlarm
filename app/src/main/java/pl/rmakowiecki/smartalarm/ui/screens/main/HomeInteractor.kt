package pl.rmakowiecki.smartalarm.ui.screens.main

import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor() {

    private var viewStateIntentsObservable: Observable<HomeViewState> = Observable.empty()

    fun getViewStateObservable() = viewStateIntentsObservable

    fun attachTabSwitchIntent(intent: Observable<Int>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intent.map(::HomeViewState)
        )
    }
}