package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import javax.inject.Inject

class AlarmViewStateReducer @Inject constructor() {

    fun reduce(currentViewState: AlarmIncidentsViewState, change: AlarmIncidentsViewStateChange): AlarmIncidentsViewState = when (change) {
        is AlarmIncidentsViewStateChange.ItemArchived -> {
            currentViewState
        }
        is AlarmIncidentsViewStateChange.ItemDeleted -> {
            currentViewState
        }
        is AlarmIncidentsViewStateChange.ItemsChanged -> {
            currentViewState.copy(isLoading = false, incidentsList = change.newList, isPlaceholderVisible = false)
        }
        is AlarmIncidentsViewStateChange.ItemsEmpty -> {
            if (change.isEmpty) {
                currentViewState.copy(isLoading = false, isPlaceholderVisible = true, incidentsList = emptyList())
            } else currentViewState
        }
    }
}