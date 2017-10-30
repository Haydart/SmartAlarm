package pl.rmakowiecki.smartalarm

import android.app.Application
import android.content.Context
import com.facebook.FacebookSdk
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import pl.rmakowiecki.smartalarm.di.component.ApplicationComponent
import pl.rmakowiecki.smartalarm.di.component.DaggerApplicationComponent
import pl.rmakowiecki.smartalarm.di.module.ApplicationModule

class SmartAlarmApp : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("bD8WvhHsOJhAQ3KBhJ83QXu8Bf03")

        FacebookSdk.sdkInitialize(applicationContext)
    }

    companion object {
        fun get(context: Context) = context.applicationContext as SmartAlarmApp
    }
}