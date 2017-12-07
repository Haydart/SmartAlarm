package pl.rmakowiecki.smartalarm.feature.screens.main.alarmstate

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmStateView : Contracts.View {

    val alarmArmingIntent: Observable<Unit>
    val alarmDisarmingIntent: Observable<Unit>

    fun render(viewState: AlarmStateViewState)
}

data class AlarmStateViewState(
        val isInitializing: Boolean = true,
        val isStateChangeLoading: Boolean = true,
        val isAlarmArmed: Boolean = false,
        val alarmStateDescriptionText: String = "",
        val alarmStateButtonText: String = ""
) : Contracts.ViewState