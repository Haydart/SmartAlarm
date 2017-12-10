package pl.rmakowiecki.smartalarm.feature.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingFragment
import pl.rmakowiecki.smartalarm.feature.main.alarmincidents.AlarmIncidentsFragment
import pl.rmakowiecki.smartalarm.feature.main.settings.SettingsFragment

private const val FRAGMENT_COUNT = 3

class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> AlarmArmingFragment.newInstance()
        1 -> AlarmIncidentsFragment.newInstance()
        2 -> SettingsFragment.newInstance()
        else -> throw IllegalStateException("Only $FRAGMENT_COUNT fragments were defined for use.")
    }

    override fun getCount() = FRAGMENT_COUNT
}