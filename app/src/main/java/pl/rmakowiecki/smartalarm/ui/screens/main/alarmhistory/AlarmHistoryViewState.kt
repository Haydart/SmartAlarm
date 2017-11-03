package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

class AlarmHistoryViewState(
        val isLoading: Boolean = true,
        val incidentsList: List<SecurityIncidentItemViewState> = emptyList()
)