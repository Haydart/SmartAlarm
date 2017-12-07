package pl.rmakowiecki.smartalarm.feature.screens.incidentdetails

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.data.incidentdetails.FirebaseIncidentDetailPhotosService
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.DetailsGateway
import javax.inject.Inject

class IncidentDetailsInteractor @Inject constructor(
        private val photosService: FirebaseIncidentDetailPhotosService,
        private val reducer: IncidentDetailsViewStateReducer,
        private val detailsGateway: DetailsGateway
) {

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

    fun getViewStateObservable(): Observable<IncidentDetailsViewState> = viewStateObservable
            .scan(IncidentDetailsViewState(), reducer::reduce)

    fun attachPhotoSwipeIntent(intent: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intent
                        .map(IncidentDetailsViewStateChange::CurrentPhotoChanged))
    }
}