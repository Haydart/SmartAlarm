package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import javax.inject.Inject

class AlarmHistoryFragment : MviFragment<AlarmHistory.View, Contracts.ViewState, AlarmHistoryPresenter>() {

    @Inject lateinit var presenter: AlarmHistoryPresenter

    override val layout = R.layout.fragment_alarm_history

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    companion object {
        fun newInstance() = AlarmHistoryFragment()
    }
}

