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

    private val childEventsPublishSubject = PublishSubject.create<Pair<SecurityIncident, IncidentOperation>>()

    private fun getCurrentBackendUser() = FirebaseAuth.getInstance().currentUser

    override fun isIncidentsListEmpty(): Single<Boolean> = Single.create { emitter ->
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                .child("incidents")
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot?) = emitter.onSuccess(dataSnapshot?.value == null)

                    override fun onCancelled(p0: DatabaseError?) = Unit
                })
    }

    override fun observeIncidentsChanges(): Observable<List<SecurityIncident>> = Observable.create { emitter ->
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                .child("incidents")
                .addChildEventListener(object : ChildEventListener {

                    override fun onChildMoved(dataSnapshot: DataSnapshot?, predecessor: String?) = Unit

                    override fun onChildChanged(dataSnapshot: DataSnapshot?, predecessor: String?) = childEventsPublishSubject.onNext(Pair(
                            dataSnapshot?.getValue(SecurityIncident::class.java)!!,
                            IncidentOperation.Updated())
                    )

                    override fun onChildAdded(dataSnapshot: DataSnapshot?, predecessor: String?) {
                        incidentsList += dataSnapshot?.getValue(SecurityIncident::class.java)!!
                        emitter.onNext(incidentsList)

                        childEventsPublishSubject.onNext(Pair(
                                dataSnapshot?.getValue(SecurityIncident::class.java)!!,
                                IncidentOperation.Added())
                        )
                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot?) = childEventsPublishSubject.onNext(Pair(
                            dataSnapshot?.getValue(SecurityIncident::class.java)!!,
                            IncidentOperation.Removed())
                    )

                    override fun onCancelled(p0: DatabaseError?) = Unit
                })
    }

    override fun archiveIncident(listPosition: Int): Single<Boolean> {
        //todo implement
        return Single.just(false)
    }

    override fun deleteIncident(listPosition: Int): Single<Boolean> = Single.create { emitter ->
        userDatabaseNode.child("coredevice")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        rootDatabaseNode
                                .child(dataSnapshot?.value as String) //user's core device uid
                                .child("incidents")
                                .orderByChild("timestamp")
                                .equalTo("") //todo implement
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(p0: DataSnapshot?) {
                                        dataSnapshot.ref
                                                .removeValue()
                                                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
                                    }

                                    override fun onCancelled(p0: DatabaseError?) = Unit
                                })
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })
    }
}

sealed class IncidentOperation {
    class Added : IncidentOperation()
    class Removed : IncidentOperation()
    class Updated : IncidentOperation()
}