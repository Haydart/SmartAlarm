package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmStateView : Contracts.View {

    val alarmArmingIntent: Observable<Unit>
    val alarmDisarmingIntent: Observable<Unit>

    fun render(viewState: AlarmStateViewState)
}