package pl.rmakowiecki.smartalarm.ui.screens.main.alarmstate

import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment

class AlarmStateFragment : MviFragment<AlarmStateView, Contracts.ViewState, AlarmStatePresenter>() {

    override val layout = R.layout.fragment_alarm_state

    override fun injectComponents() = fragmentComponent.inject(this)

    companion object {
        fun newInstance() = AlarmStateFragment()
    }
}