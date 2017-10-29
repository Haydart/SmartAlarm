package pl.rmakowiecki.smartalarm.di.module

import dagger.Subcomponent
import pl.rmakowiecki.smartalarm.di.FragmentScope
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmHistoryFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmstate.AlarmStateFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsFragment

@FragmentScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface FragmentComponent {

    fun inject(alarmStateFragment: AlarmStateFragment)
    fun inject(alarmHistoryFragment: AlarmHistoryFragment)
    fun inject(settingsFragment: SettingsFragment)
}