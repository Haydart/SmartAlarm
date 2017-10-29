package pl.rmakowiecki.smartalarm.ui.screens.main

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Home {

    interface View : Contracts.View {
        val tabSwitchIntent: Observable<Int>

        fun render(viewState: HomeViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<HomeViewState>

        fun attachTabSwitchIntent(intentObservable: Observable<Int>)
    }
}