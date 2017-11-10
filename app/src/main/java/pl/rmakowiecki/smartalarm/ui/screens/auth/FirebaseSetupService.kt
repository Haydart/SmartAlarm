package pl.rmakowiecki.smartalarm.ui.screens.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSetupService @Inject constructor() : SetupService {

    private val rootDatabaseNode = FirebaseDatabase.getInstance()
            .reference

    private val userDatabaseNode
        get() = rootDatabaseNode
                .child("users")
                .child(getCurrentBackendUser()?.uid ?: "")

    private fun getCurrentBackendUser() = FirebaseAuth.getInstance().currentUser

    override fun fetchUsersCoreDeviceUid(): Single<String> = Single.create { emitter ->
        userDatabaseNode
                .child("coredevice")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val coreDeviceUid = dataSnapshot.value as? String ?: ""
                        emitter.onSuccess(coreDeviceUid)
                    }

                    override fun onCancelled(dataSnapshot: DatabaseError?) = Unit
                })
    }

    override fun updateCoreDeviceUid(uniqueIdentifier: String): Single<Boolean> = Single.create { emitter ->
        userDatabaseNode
                .child("coredevice")
                .setValue(uniqueIdentifier)
                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
    }

    override fun checkIfCoreDeviceExists(uniqueIdentifier: String): Single<Boolean> = Single.create { emitter ->
        rootDatabaseNode
                .child(uniqueIdentifier)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) = emitter.onSuccess(dataSnapshot.exists())

                    override fun onCancelled(error: DatabaseError?) = Unit
                })
    }
}

interface SetupService {

    fun fetchUsersCoreDeviceUid(): Single<String>
    fun updateCoreDeviceUid(uniqueIdentifier: String): Single<Boolean>
    fun checkIfCoreDeviceExists(uniqueIdentifier: String): Single<Boolean>
}