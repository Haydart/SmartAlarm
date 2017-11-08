package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface IncidentDetails {
    interface View : Contracts.View {
        val photoSwipeIntent: Observable<Int>

        fun render(viewState: IncidentDetailsViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<IncidentDetailsViewState>
    }
}