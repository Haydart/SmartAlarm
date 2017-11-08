package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class IncidentDetailsPresenter @Inject constructor(
        private val interactor: IncidentDetailsInteractor
) : MviPresenter<IncidentDetails.View, IncidentDetailsViewState>() {

    override fun bindIntents() = with(interactor) {

        subscribeViewState(getViewStateObservable(), IncidentDetails.View::render)
    }
}