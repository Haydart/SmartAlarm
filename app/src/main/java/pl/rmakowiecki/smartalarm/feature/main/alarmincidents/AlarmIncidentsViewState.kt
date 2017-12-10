package pl.rmakowiecki.smartalarm.feature.main.alarmincidents

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.domain.main.alarmincidents.SecurityIncidentItemViewState

data class AlarmIncidentsViewState(
        val isLoading: Boolean = true,
        val isPlaceholderVisible: Boolean = false,
        val incidentsList: List<SecurityIncidentItemViewState> = emptyList(),
        val isSnackBarShown: Boolean = false,
        val snackBarMessage: String? = null
) : Contracts.ViewState