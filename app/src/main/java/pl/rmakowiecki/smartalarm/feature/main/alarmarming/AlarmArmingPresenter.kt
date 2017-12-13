package pl.rmakowiecki.smartalarm.feature.main.alarmarming

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmArmingPresenter @Inject constructor(
        private val interactor: AlarmArmingInteractor
) : MviPresenter<AlarmArmingView, AlarmArmingViewState>() {

    override fun bindIntents() = with(interactor) {

        attachAlarmArmingIntent(
                bindIntent(AlarmArmingView::alarmArmingIntent))

        attachAlarmDisarmingIntent(
                bindIntent(AlarmArmingView::alarmDisarmingIntent))

        subscribeViewState(viewStateObservable, AlarmArmingView::render)
    }
}