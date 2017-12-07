package pl.rmakowiecki.smartalarm.feature.screens.main.alarmstate

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_alarm_state.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment

class AlarmStateFragment : MviFragment<AlarmStateView, Contracts.ViewState, AlarmStatePresenter>(), AlarmStateView {

    override val layout = R.layout.fragment_alarm_state

    override val alarmArmingIntent: Observable<TargetAlarmState>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        armingButton.isEnabled = true
        disarmingButton.isEnabled = true
    }

    override fun render(viewState: AlarmStateViewState) {
        //todo implement
    }

    companion object {
        fun newInstance() = AlarmStateFragment()
    }
}