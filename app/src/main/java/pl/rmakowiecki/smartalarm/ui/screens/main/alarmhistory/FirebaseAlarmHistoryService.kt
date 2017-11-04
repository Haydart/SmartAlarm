package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import javax.inject.Inject

class FirebaseAlarmHistoryService @Inject constructor() : AlarmHistoryService {

    private var coreDeviceUid: String? = null

    private val rootDatabaseNode = FirebaseDatabase
            .getInstance()
            .reference

    private val userDatabaseNode = rootDatabaseNode
            .child("users")
            .child(getCurrentBackendUser()?.uid)

    private fun getCurrentBackendUser() = FirebaseAuth.getInstance().currentUser

    override fun archiveIncident(securityIncident: SecurityIncident): Single<Boolean> {
        return Single.just(false) //todo implement
    }

    override fun deleteIncident(securityIncident: SecurityIncident): Single<Boolean> = Single.create { emitter ->
        userDatabaseNode.child("coredevice")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        rootDatabaseNode
                                .child(dataSnapshot?.value as String) //user's core device uid
                                .child("incidents")
                                .orderByChild("timestamp")
                                .equalTo(securityIncident.timestamp as Double)
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