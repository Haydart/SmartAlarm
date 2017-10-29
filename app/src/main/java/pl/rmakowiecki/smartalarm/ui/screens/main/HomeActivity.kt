package pl.rmakowiecki.smartalarm.ui.screens.main

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.activity_home.*
import pl.rmakowiecki.smartalarm.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

        bottomNavigationBar.accentColor = ContextCompat.getColor(this, R.color.accent)
        bottomNavigationBar.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE

        arrayListOf<AHBottomNavigationItem>().apply {
            add(AHBottomNavigationItem(R.string.bottom_bar_state, R.drawable.ic_settings_power_white_24px, R.color.primary))
            add(AHBottomNavigationItem(R.string.bottom_bar_history, R.drawable.ic_history_white_24px, R.color.primary))
            add(AHBottomNavigationItem(R.string.bottom_bar_settings, R.drawable.ic_settings_white_24px, R.color.primary))
        }.forEach {
            bottomNavigationBar.addItem(it)
        }
    }
}
