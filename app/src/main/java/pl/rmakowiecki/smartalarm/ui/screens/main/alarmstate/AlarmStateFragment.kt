package pl.rmakowiecki.smartalarm.ui.screens.main.alarmstate

import android.os.Bundle
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import javax.inject.Inject

class AlarmStateFragment : MviFragment<AlarmState.View, Contracts.ViewState, AlarmStatePresenter>() {

    @Inject lateinit var presenter: AlarmStatePresenter

    override val layout = R.layout.fragment_alarm_state

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance() = AlarmStateFragment()
    }
}