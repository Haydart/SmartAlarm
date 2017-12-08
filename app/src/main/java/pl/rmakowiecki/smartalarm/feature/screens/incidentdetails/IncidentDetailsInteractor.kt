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

    private var viewStateChanges = Observable.empty<IncidentDetailsViewStateChange>()

    val viewStateObservable: Observable<IncidentDetailsViewState>
        get() = viewStateChanges
                .scan(IncidentDetailsViewState(), reducer::reduce)

    init {
        observeIncidentPhotos()
    }

    private fun observeIncidentPhotos() = mergeChanges(
            detailsGateway
                    .incidentBackendIdSingle
                    .toObservable()
                    .flatMap { incidentId -> photosService.observePhotos(incidentId) }
                    .map(IncidentDetailsViewStateChange::PhotoAdded)
    )

    fun attachPhotoSwipeIntent(intentObservable: Observable<Int>) = mergeChanges(
            intentObservable.map(IncidentDetailsViewStateChange::CurrentPhotoChanged)
    )

    private fun <T : IncidentDetailsViewStateChange> mergeChanges(vararg changes: Observable<out T>) = changes.forEach {
        viewStateChanges = viewStateChanges.mergeWith(it)
    }
}