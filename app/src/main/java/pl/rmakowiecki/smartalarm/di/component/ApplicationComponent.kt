package pl.rmakowiecki.smartalarm.di.component

import dagger.Component
import pl.rmakowiecki.smartalarm.di.module.ActivityModule
import pl.rmakowiecki.smartalarm.di.module.ApplicationModule
import pl.rmakowiecki.smartalarm.di.module.FragmentComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(activityModule: ActivityModule): FragmentComponent
}