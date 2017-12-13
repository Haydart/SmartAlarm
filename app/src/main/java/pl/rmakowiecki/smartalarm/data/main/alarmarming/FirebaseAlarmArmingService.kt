package pl.rmakowiecki.smartalarm.data.main.alarmarming

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import javax.inject.Inject

class FirebaseAlarmArmingService @Inject constructor() : AlarmArmingService {

    private val rootDatabaseNode = FirebaseDatabase
            .getInstance()
            .reference

    private val userDatabaseNode = rootDatabaseNode
            .child("users")
            .child(getCurrentBackendUser()?.uid)

    private fun getCurrentBackendUser() = FirebaseAuth.getInstance().currentUser

    override fun updateAlarmState(armed: Boolean): Single<Boolean> = Single.create { emitter ->
        userDatabaseNode
                .child("coredevice")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        rootDatabaseNode
                                .child(dataSnapshot.value as String)
                                .child("state")
                                .child("active")
                                .setValue(armed)
                                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
                    }

                    override fun onCancelled(p0: DatabaseError?) = Unit
                })
    }
}