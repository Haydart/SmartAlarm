package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_alarm_state.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment

class AlarmArmingFragment : MviFragment<AlarmArmingView, AlarmArmingViewState, AlarmArmingPresenter>(), AlarmArmingView {

    override val layout = R.layout.fragment_alarm_state

    override val alarmArmingIntent: Observable<Unit>
        get() = armingButton.clicks()

    override val alarmDisarmingIntent: Observable<Unit>
        get() = disarmingButton.clicks()

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        armingButton.isEnabled = true
        disarmingButton.isEnabled = true
    }

    override fun render(viewState: AlarmArmingViewState) {
        //todo implement
    }

    companion object {
        fun newInstance() = AlarmArmingFragment()
    }
}