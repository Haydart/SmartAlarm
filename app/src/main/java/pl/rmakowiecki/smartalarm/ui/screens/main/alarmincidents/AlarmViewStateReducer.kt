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
    }
}