package pl.rmakowiecki.smartalarm.feature.screens.incidentdetails

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class IncidentDetailsPresenter @Inject constructor(
        private val interactor: IncidentDetailsInteractor
) : MviPresenter<IncidentDetailsView, IncidentDetailsViewState>() {

    override fun bindIntents() = with(interactor) {
        attachPhotoSwipeIntent(
                intent(IncidentDetailsView::photoSwipeIntent)
        )

        subscribeViewState(getViewStateObservable(), IncidentDetailsView::render)
    }
}