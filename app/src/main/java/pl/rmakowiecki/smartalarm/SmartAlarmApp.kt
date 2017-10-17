package pl.rmakowiecki.smartalarm

import android.app.Application
import com.facebook.FacebookSdk
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class SmartAlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("8XwDg5Fxo0WszCCEaMCWusoq0vu1")

        FacebookSdk.sdkInitialize(applicationContext)
    }
}