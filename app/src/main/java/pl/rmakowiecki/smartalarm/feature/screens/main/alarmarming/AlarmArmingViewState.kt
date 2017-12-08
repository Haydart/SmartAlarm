package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import pl.rmakowiecki.smartalarm.base.Contracts

data class AlarmArmingViewState(
        val isInitializing: Boolean = true,
        val isAlarmArmed: Boolean = false
) : Contracts.ViewState