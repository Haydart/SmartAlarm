package pl.rmakowiecki.smartalarm

import android.app.Application
import com.facebook.FacebookSdk
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class SmartAlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("NiLVrDuQU6exqaPK9WZzfx5VIzZ2")

        FacebookSdk.sdkInitialize(applicationContext)
    }
}