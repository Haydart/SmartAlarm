package pl.rmakowiecki.smartalarm.feature.main.alarmarming

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmArmingView : Contracts.View {

    val alarmArmingIntent: Observable<Unit>
    val alarmDisarmingIntent: Observable<Unit>

    fun render(viewState: AlarmArmingViewState)
}