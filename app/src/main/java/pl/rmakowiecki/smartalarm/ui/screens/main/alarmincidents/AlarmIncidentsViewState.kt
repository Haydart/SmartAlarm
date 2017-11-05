package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import pl.rmakowiecki.smartalarm.base.Contracts

class AlarmIncidentsViewState(
        val isLoading: Boolean = true,
        val incidentsList: List<SecurityIncidentItemViewState> = emptyList()
) : Contracts.ViewState