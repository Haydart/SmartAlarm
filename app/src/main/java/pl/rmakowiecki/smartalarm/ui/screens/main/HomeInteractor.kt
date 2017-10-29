package pl.rmakowiecki.smartalarm.ui.screens.main

import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractor @Inject constructor() : Home.Interactor {

    private var viewStateIntentsObservable: Observable<HomeViewState> = Observable.empty()

    override fun getViewStateObservable() = viewStateIntentsObservable

    override fun attachTabSwitchIntent(intentObservable: Observable<Int>) {
        viewStateIntentsObservable = viewStateIntentsObservable.mergeWith(
                intentObservable.map(::HomeViewState)
        )
    }
}