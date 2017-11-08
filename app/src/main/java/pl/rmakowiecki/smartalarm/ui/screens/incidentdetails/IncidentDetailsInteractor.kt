package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.DetailsGateway
import javax.inject.Inject

class IncidentDetailsInteractor @Inject constructor(
        private val photosService: FirebaseIncidentDetailPhotosService,
        private val reducer: IncidentDetailsViewStateReducer,
        private val detailsGateway: DetailsGateway
) : IncidentDetails.Interactor {

    private var viewStateObservable = Observable.empty<IncidentDetailsViewStateChange>()

    init {
        observeIncidentPhotos()
    }

    private fun observeIncidentPhotos() {
        viewStateObservable = viewStateObservable
                .mergeWith(detailsGateway
                        .incidentBackendIdSingle
                        .toObservable()
                        .flatMap { incidentId -> photosService.observePhotos(incidentId) }
                        .map(IncidentDetailsViewStateChange::PhotoAdded))
    }

    override fun getViewStateObservable(): Observable<IncidentDetailsViewState> = viewStateObservable
            .scan(IncidentDetailsViewState(), reducer::reduce)

    override fun attachPhotoSwipeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map(IncidentDetailsViewStateChange::CurrentPhotoChanged))
    }
}