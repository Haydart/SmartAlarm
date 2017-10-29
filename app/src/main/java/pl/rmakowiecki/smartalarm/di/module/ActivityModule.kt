package pl.rmakowiecki.smartalarm.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideActivity() = activity
}