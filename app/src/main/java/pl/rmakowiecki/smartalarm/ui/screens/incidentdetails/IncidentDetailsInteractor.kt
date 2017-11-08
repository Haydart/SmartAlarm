package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.DetailsGateway
import javax.inject.Inject
import javax.inject.Singleton

class IncidentDetailsInteractor @Inject constructor(
        private val photosService: FirebaseIncidentDetailPhotosService,
        private val reducer: IncidentDetailsViewStateReducer,
        private val detailsGateway: DetailsGateway
) : IncidentDetails.Interactor {

    private var viewStateObservable = Observable.empty<IncidentDetailsViewStateChange>()

    override fun getViewStateObservable(): Observable<IncidentDetailsViewState> = viewStateObservable
            .scan(IncidentDetailsViewState(), reducer::reduce)

    override fun attachPhotoSwipeIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map(IncidentDetailsViewStateChange::CurrentPhotoChanged))
    }
}

@Singleton
class FirebaseIncidentDetailPhotosService @Inject constructor() : IncidentDetailPhotosService {

    private val storageNode = FirebaseStorage
            .getInstance()
            .getReferenceFromUrl("gs://smartalarmcore.appspot.com")

    private val rootDatabaseNode = FirebaseDatabase
            .getInstance()
            .reference

    override fun observePhotos(photoIncidentBackendId: String): Observable<String> = Observable.create {
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                .child("incidents")
                .child(photoIncidentBackendId)
    }
}

interface IncidentDetailPhotosService {

    fun observePhotos(photoIncidentBackendId: String): Observable<String>
}

class IncidentDetailsViewStateReducer @Inject constructor() {

    fun reduce(currentState: IncidentDetailsViewState, change: IncidentDetailsViewStateChange) = when (change) {
        is IncidentDetailsViewStateChange.CurrentPhotoChanged -> {
            currentState.copy(currentPhotoPage = change.newPosition)
        }
        is IncidentDetailsViewStateChange.PhotosListChanged -> {
            currentState.copy(photoUrlsList = change.newList)
        }
    }
}

sealed class IncidentDetailsViewStateChange {
    class CurrentPhotoChanged(val newPosition: Int) : IncidentDetailsViewStateChange()
    class PhotosListChanged(val newList: List<String>) : IncidentDetailsViewStateChange()
}
