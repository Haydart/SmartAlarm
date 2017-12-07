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
import javax.inject.Inject

class HomeActivity : MviActivity<HomeView, HomeViewState, HomePresenter>(), HomeView {

    @Inject lateinit var presenter: HomePresenter

    override val layoutRes = R.layout.activity_home

    private val tabSwitchPublishSubject = PublishSubject.create<Int>()

    override val tabSwitchIntent: Observable<Int> = tabSwitchPublishSubject

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNavigationBar()
        setupFragmentContainer()
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

    private fun setupFragmentContainer() {
        fragmentViewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }

    private fun createNavigationItems() = arrayListOf<AHBottomNavigationItem>().apply {
        add(AHBottomNavigationItem(R.string.title_alarm_state, R.drawable.ic_settings_power_white_24px, R.color.primary))
        add(AHBottomNavigationItem(R.string.title_security_incidents, R.drawable.ic_history_white_24px, R.color.primary))
        add(AHBottomNavigationItem(R.string.title_settings, R.drawable.ic_settings_white_24px, R.color.primary))
    }

    override fun render(viewState: HomeViewState) = with(viewState) {
        bottomNavigationBar.setCurrentItem(selectedTabPosition, false)
        toolbarTitle.text = bottomNavigationBar.getItem(selectedTabPosition).getTitle(this@HomeActivity)

        fragmentViewPager.setCurrentItem(selectedTabPosition, false)
    }
}