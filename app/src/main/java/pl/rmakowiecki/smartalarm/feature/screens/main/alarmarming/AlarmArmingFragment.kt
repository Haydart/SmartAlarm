package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_alarm_state.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import pl.rmakowiecki.smartalarm.extensions.enable
import pl.rmakowiecki.smartalarm.extensions.invisible
import pl.rmakowiecki.smartalarm.extensions.visible

class AlarmArmingFragment : MviFragment<AlarmArmingView, AlarmArmingViewState, AlarmArmingPresenter>(), AlarmArmingView {

    override val layout = R.layout.fragment_alarm_state

    override val alarmArmingIntent: Observable<Unit>
        get() = armingButton.clicks()

    override val alarmDisarmingIntent: Observable<Unit>
        get() = disarmingButton.clicks()

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        armingButton.enable()
        disarmingButton.enable()
    }

    override fun render(viewState: AlarmArmingViewState) = with(viewState) {
        if (isInitializing) {
            armingButton.invisible()
            disarmingButton.invisible()
            alarmIdleStateLayout.invisible()
            progressBar.visible()
        } else {
            alarmIdleStateLayout.visible()
            progressBar.invisible()
        }

        if (isAlarmArmed) {
            armingButton.invisible()
            disarmingButton.visible()
            alarmStateImage.background = ContextCompat.getDrawable(context, R.drawable.ic_power_active)
            alarmStateText.text = getString(R.string.alarm_state_armed)
        } else {
            armingButton.visible()
            disarmingButton.invisible()
            alarmStateImage.background = ContextCompat.getDrawable(context, R.drawable.ic_power_inactive)
            alarmStateText.text = getString(R.string.alarm_state_disarmed)
        }
    }

    companion object {
        fun newInstance() = AlarmArmingFragment()
    }
}