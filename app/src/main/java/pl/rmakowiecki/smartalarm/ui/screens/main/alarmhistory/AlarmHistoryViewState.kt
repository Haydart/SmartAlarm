package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import pl.rmakowiecki.smartalarm.base.Contracts

class AlarmHistoryViewState(
        val isLoading: Boolean = true,
        val incidentsList: List<SecurityIncidentItemViewState> = emptyList()
) : Contracts.ViewState