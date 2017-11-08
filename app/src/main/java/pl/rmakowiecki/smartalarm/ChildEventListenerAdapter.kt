package pl.rmakowiecki.smartalarm

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

abstract class ChildEventListenerAdapter : ChildEventListener {
    override fun onCancelled(dataSnapshot: DatabaseError?) = Unit

    override fun onChildMoved(dataSnapshot: DataSnapshot, predecessor: String?) = Unit

    override fun onChildChanged(dataSnapshot: DataSnapshot, predecessor: String?) = Unit

    override fun onChildAdded(dataSnapshot: DataSnapshot, predecessor: String?) = Unit

    override fun onChildRemoved(dataSnapshot: DataSnapshot) = Unit
}