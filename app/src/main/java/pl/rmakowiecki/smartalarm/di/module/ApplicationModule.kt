package pl.rmakowiecki.smartalarm.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.rmakowiecki.smartalarm.di.qualifier.AppContext

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @AppContext
    fun provideAppContext(): Context = application
}