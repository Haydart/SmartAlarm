package pl.rmakowiecki.smartalarm.di.module

import dagger.Subcomponent
import pl.rmakowiecki.smartalarm.di.FragmentScope
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.AlarmIncidentsFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmstate.AlarmStateFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsFragment

@FragmentScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface FragmentComponent {

    fun inject(alarmStateFragment: AlarmStateFragment)
    fun inject(alarmIncidentsFragment: AlarmIncidentsFragment)
    fun inject(settingsFragment: SettingsFragment)
}