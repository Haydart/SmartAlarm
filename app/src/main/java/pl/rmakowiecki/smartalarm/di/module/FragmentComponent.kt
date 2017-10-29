package pl.rmakowiecki.smartalarm.di.module

import dagger.Subcomponent
import pl.rmakowiecki.smartalarm.di.FragmentScope
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmHistoryFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsFragment

@FragmentScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface FragmentComponent {

    fun inject(settingsFragment: SettingsFragment)
    fun inject(alarmHistoryFragment: AlarmHistoryFragment)
}