package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import io.reactivex.Observable
import javax.inject.Inject

class IncidentDetailsInteractor @Inject constructor() : IncidentDetails.Interactor {

    private val viewStateObservable = Observable.empty<IncidentDetailsViewState>()

    override fun getViewStateObservable(): Observable<IncidentDetailsViewState> = viewStateObservable
}