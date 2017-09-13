package pl.rmakowiecki.smartalarm

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class SmartAlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("Alarm_Notifications")
    }
}