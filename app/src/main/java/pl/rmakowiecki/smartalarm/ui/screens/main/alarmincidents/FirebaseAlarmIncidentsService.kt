package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class FirebaseAlarmIncidentsService @Inject constructor() : AlarmIncidentsService {

    private val rootDatabaseNode = FirebaseDatabase
            .getInstance()
            .reference

    private val userDatabaseNode = rootDatabaseNode
            .child("users")
            .child(getCurrentBackendUser()?.uid)

    private val incidentsList = mutableListOf<SecurityIncident>()

    private val childEventsPublishSubject = PublishSubject.create<IncidentChange>()

    private fun getCurrentBackendUser() = FirebaseAuth.getInstance().currentUser

    override fun isIncidentsListEmpty(): Single<Boolean> = Single.create { emitter ->
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1") //todo use core device uid taken from server
                .child("incidents")
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot?) = emitter.onSuccess(dataSnapshot?.value == null)

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })
    }

    override fun fetchIdForListPosition(listPosition: Int): Single<String> = Single.create { emitter ->
        userDatabaseNode.child("coredevice")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(currentUserUidSnapshot: DataSnapshot) {
                        rootDatabaseNode
                                .child(currentUserUidSnapshot.value as String)
                                .child("incidents")
                                .orderByChild("timestamp")
                                .equalTo(incidentsList[listPosition].timestamp.toDouble())
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        emitter.onSuccess(dataSnapshot.children.first().ref.key)
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) = Unit
                                })
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })
    }

    override fun observeIncidentsChanges(): Observable<IncidentChange> {
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1") //todo use core device uid taken from server
                .child("incidents")
                .addChildEventListener(object : ChildEventListener {

                    override fun onChildMoved(dataSnapshot: DataSnapshot, predecessor: String?) = Unit

                    override fun onChildChanged(dataSnapshot: DataSnapshot, predecessor: String?) {
                        val newModel = dataSnapshot.getValue(SecurityIncident::class.java)!!
                        val changedIndex = incidentsList.indexOfFirst { it.timestamp == newModel.timestamp }
                        incidentsList[changedIndex] = newModel

                        childEventsPublishSubject.onNext(
                                IncidentChange(
                                        newModel,
                                        IncidentOperation.Updated(changedIndex)
                                )
                        )
                    }

                    override fun onChildAdded(dataSnapshot: DataSnapshot, predecessor: String?) {
                        incidentsList += dataSnapshot.getValue(SecurityIncident::class.java)!!

                        childEventsPublishSubject.onNext(
                                IncidentChange(
                                        dataSnapshot.getValue(SecurityIncident::class.java)!!,
                                        IncidentOperation.Added()
                                )
                        )
                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                        val removedModel = dataSnapshot.getValue(SecurityIncident::class.java)!!
                        val removedIndex = incidentsList.indexOf(removedModel)

                        incidentsList -= removedModel

                        childEventsPublishSubject.onNext(
                                IncidentChange(
                                        removedModel,
                                        IncidentOperation.Removed(removedIndex)
                                )
                        )
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })

        return childEventsPublishSubject
    }

    override fun archiveIncident(listPosition: Int): Single<Boolean> {
        //todo implement
        return Single.just(false)
    }

    override fun deleteIncident(listPosition: Int): Single<Boolean> = Single.create { emitter ->
        userDatabaseNode.child("coredevice")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(currentUserUidSnapshot: DataSnapshot) {
                        rootDatabaseNode
                                .child(currentUserUidSnapshot.value as String)
                                .child("incidents")
                                .orderByChild("timestamp")
                                .equalTo(incidentsList[listPosition].timestamp.toDouble())
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        dataSnapshot.children.forEach { incidentSnapshot ->
                                            incidentSnapshot.ref
                                                    .removeValue()
                                                    .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) = Unit
                                })
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })
    }
}

class IncidentChange(
        val model: SecurityIncident,
        val operation: IncidentOperation
)

sealed class IncidentOperation {
    class Added : IncidentOperation()
    class Removed(val removedIndex: Int) : IncidentOperation()
    class Updated(val changedIndex: Int) : IncidentOperation()
}