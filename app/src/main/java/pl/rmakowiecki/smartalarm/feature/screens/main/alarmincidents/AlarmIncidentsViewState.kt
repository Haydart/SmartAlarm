package pl.rmakowiecki.smartalarm.feature.screens.main.alarmincidents

import pl.rmakowiecki.smartalarm.base.Contracts

data class AlarmIncidentsViewState(
        val isLoading: Boolean = true,
        val isPlaceholderVisible: Boolean = false,
        val incidentsList: List<SecurityIncidentItemViewState> = emptyList(),
        val isSnackBarShown: Boolean = false,
        val snackBarMessage: String? = null
) : Contracts.ViewState