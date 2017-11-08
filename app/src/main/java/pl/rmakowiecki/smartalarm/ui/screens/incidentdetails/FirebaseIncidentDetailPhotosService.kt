package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ChildEventListenerAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseIncidentDetailPhotosService @Inject constructor() : IncidentDetailPhotosService {

    private val storageNode = FirebaseStorage.getInstance()
            .getReferenceFromUrl("gs://smartalarmcore.appspot.com")

    private val rootDatabaseNode = FirebaseDatabase.getInstance()
            .reference

    override fun observePhotos(photoIncidentBackendId: String): Observable<String> = Observable.create { emitter ->

        val incidentPhotosChildEventListener = object : ChildEventListenerAdapter() {
            override fun onChildAdded(dataSnapshot: DataSnapshot, predecessor: String?) {
                if (dataSnapshot.value == true) {
                    val photoFileName = "$photoIncidentBackendId#${dataSnapshot.key}.jpg"

                    storageNode.child("core_assets")
                            .child("images")
                            .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                            .child(photoFileName)
                            .downloadUrl
                            .addOnCompleteListener {
                                emitter.onNext(it.result.toString())
                            }
                }
            }

        }

        val photosNode = rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                .child("incidents")
                .child(photoIncidentBackendId)
                .child("photos")

        photosNode.addChildEventListener(incidentPhotosChildEventListener)

        emitter.setCancellable { photosNode.removeEventListener(incidentPhotosChildEventListener) }
    }
}

interface IncidentDetailPhotosService {

    fun observePhotos(photoIncidentBackendId: String): Observable<String>
}