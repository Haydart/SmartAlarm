package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import pl.rmakowiecki.smartalarm.base.Contracts

data class AlarmStateViewState(
        val isInitializing: Boolean = true,
        val isStateChangeLoading: Boolean = true,
        val isAlarmArmed: Boolean = false,
        val alarmStateDescriptionText: String = "",
        val alarmStateButtonText: String = ""
) : Contracts.ViewState