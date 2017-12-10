package pl.rmakowiecki.smartalarm.feature.incidentdetails

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface IncidentDetailsView : Contracts.View {

    val photoSwipeIntent: Observable<Int>

    fun render(viewState: IncidentDetailsViewState)
}