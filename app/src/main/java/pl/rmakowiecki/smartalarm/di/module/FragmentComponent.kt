package pl.rmakowiecki.smartalarm.di.module

import dagger.Subcomponent
import pl.rmakowiecki.smartalarm.di.FragmentScope
import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingFragment
import pl.rmakowiecki.smartalarm.feature.screens.main.alarmincidents.AlarmIncidentsFragment
import pl.rmakowiecki.smartalarm.feature.screens.main.settings.SettingsFragment

@FragmentScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface FragmentComponent {

    fun inject(alarmStateFragment: AlarmArmingFragment)
    fun inject(alarmIncidentsFragment: AlarmIncidentsFragment)
    fun inject(settingsFragment: SettingsFragment)
}