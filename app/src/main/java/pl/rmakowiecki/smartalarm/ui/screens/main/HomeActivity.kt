package pl.rmakowiecki.smartalarm.ui.screens.main

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.extensions.inTransaction
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmHistoryFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmstate.AlarmStateFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsFragment
import javax.inject.Inject

class HomeActivity : MviActivity<Home.View, HomeViewState, HomePresenter>(), Home.View {

    @Inject lateinit var presenter: HomePresenter

    override val layoutRes = R.layout.activity_home

    private val tabSwitchPublishSubject = PublishSubject.create<Int>()

    override val tabSwitchIntent: Observable<Int> = tabSwitchPublishSubject

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() = bottomNavigationBar.apply {
        accentColor = ContextCompat.getColor(this@HomeActivity, R.color.accent)
        titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        addItems(createNavigationItems())
        setOnTabSelectedListener { position, wasSelected ->
            if (!wasSelected) tabSwitchPublishSubject.onNext(position)
            true
        }
    }

    private fun createNavigationItems() = arrayListOf<AHBottomNavigationItem>().apply {
        add(AHBottomNavigationItem(R.string.bottom_bar_state, R.drawable.ic_settings_power_white_24px, R.color.primary))
        add(AHBottomNavigationItem(R.string.bottom_bar_history, R.drawable.ic_history_white_24px, R.color.primary))
        add(AHBottomNavigationItem(R.string.bottom_bar_settings, R.drawable.ic_settings_white_24px, R.color.primary))
    }

    override fun render(viewState: HomeViewState) = with(viewState) {
        bottomNavigationBar.setCurrentItem(selectedTabPosition, false)
        toolbarTitle.text = bottomNavigationBar.getItem(selectedTabPosition).getTitle(this@HomeActivity)

        supportFragmentManager.inTransaction {
            replace(R.id.fragmentContentFrame, when (selectedTabPosition) {
                0 -> AlarmStateFragment.newInstance()
                1 -> AlarmHistoryFragment.newInstance()
                2 -> SettingsFragment.newInstance()
                else -> throw IllegalStateException("Invalid menu position chosen")
            })
        }
    }
}